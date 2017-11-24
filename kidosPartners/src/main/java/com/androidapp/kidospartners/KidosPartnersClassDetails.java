package com.androidapp.kidospartners;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.androidapp.kidospartners.abstracts.KidosPartnersPrePostProcessor;
import com.androidapp.kidospartners.beans.KidosPartnersClassDetailsBean;
import com.androidapp.kidospartners.beans.KidosPartnersIndianCitiesBean;
import com.androidapp.kidospartners.databinding.ActivityKidosPartnersClassDetailsBinding;
import com.androidapp.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.androidapp.kidospartners.utils.KidosPartnersConstants;
import com.androidapp.kidospartners.utils.KidosPartnersRestClient;
import com.androidapp.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.androidapp.kidospartners.utils.KidosPartnersUtil.parseOutputAndTakeAction;

public class KidosPartnersClassDetails extends KidosPartnersPrePostProcessor implements IKidosPartnersRestClientWrapper {

	private Factory mFactory ;

	private String getClassDetailsURI=KidosPartnersConstants.GET_CLASSDETAILS_BY_ACTIVITYID_URI;
	private String saveClassDetailsURI=KidosPartnersConstants.SAVE_CLASSDETAILS_BY_ACTIVITYID_URI;
	private String listIndianCitiesURI = KidosPartnersConstants.LIST_INDIANCITIES_URI;


	private HashMap<String,KidosPartnersIndianCitiesBean> indianCitiesMap = new HashMap<String,KidosPartnersIndianCitiesBean>();
	ActivityKidosPartnersClassDetailsBinding classDetailsBinding;

	KidosPartnersClassDetailsBean classDetails =new KidosPartnersClassDetailsBean()	;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		KidosPartnerspreProcessor();

		restRequest(KidosPartnersClassDetails.this, null, KidosPartnersConstants.GET, listIndianCitiesURI);

		//if activity is already created
		if(getActivityID()!=null)
		{
			restRequest(KidosPartnersClassDetails.this, null, KidosPartnersConstants.GET, getClassDetailsURI+getActivityID());
		}
		else //if activity is not yet created, setup additional parameters needed to store at the time of activity creation
		{
			classDetails.setUserid(user.getUserid());
			classDetails.setType(category.get_id());
			classDetails.setActivityStatus(0);
		}

		classDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_kidos_partners_class_details);
		classDetailsBinding.setClassdetails(classDetails);

		mFactory= KidosPartnersUtil.getLayoutFactory(this.getApplicationContext());
		
		
		//Set Title
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(KidosPartnersUtil.setTitleText(this,KidosPartnersConstants.CLASS_DETAILS_SCREEN_TITLE , KidosPartnersConstants.TITLE_TEXT_FONTFACE));


	
	}
	
	@Override
	public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

		if(name.contains("android.support.v7.internal.view.menu.ActionMenuItemView")) {
	        LayoutInflater li = LayoutInflater.from(context);
	        View view = null;
	        try {
	            view = li.createView(name, null, attrs);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        if (view != null) {
	            if(mFactory != null) {
	                view = mFactory.onCreateView(name,context,attrs);
	            }
	            return view;
	        }
	    }
	    return super.onCreateView(name, context, attrs);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.kidos_partners_class_details, menu);
		 new MenuInflater(getApplication()).inflate(R.menu.kidos_partners_class_details, menu); 
		 return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId()==R.id.saveclassdetails) {
				saveClassDetails();
				//this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

	private void saveClassDetails()
	{
		if(validateForm()) {
			Type type = new TypeToken<Map<String, Object>>() {
			}.getType();
			Map<String, Object> classDetailsMap = new Gson().fromJson(new Gson().toJson(classDetails), type);

			restRequest(KidosPartnersClassDetails.this, classDetailsMap, KidosPartnersConstants.POST, saveClassDetailsURI);
		}

	}

	@Override
	public void restRequest(IKidosPartnersRestClientWrapper context, Map<String, Object> args, String method, String url) {
		KidosPartnersRestClient.sendRequest(context,args,method,url);
	}

	@Override
	public void restCallBack(String restOutput, String requestUrl) {
		System.out.println("In Rest Call back method of KidosPartnersClassDetails::requestUrl="+requestUrl);

		Gson gson = new Gson();

		if(requestUrl.contains(getClassDetailsURI)) {


			classDetails = gson.fromJson(restOutput, new TypeToken<KidosPartnersClassDetailsBean>() {}.getType());
			classDetailsBinding.setClassdetails(classDetails);
		}
		else if(requestUrl.contains(saveClassDetailsURI))
		{
			parseOutputAndTakeAction(restOutput,KidosPartnersClassDetails.this);
		}
		else if(requestUrl.contains(listIndianCitiesURI))
		{
			System.out.println("---> Indiancities"+restOutput);

			List<KidosPartnersIndianCitiesBean> indianCities = gson.fromJson(restOutput, new TypeToken<List<KidosPartnersIndianCitiesBean>>() {}.getType());

			convertListToMap(indianCities);

			populateStateCityAreaList();

		}
	}


	private void populateStateCityAreaList()
	{
		String[] states = indianCitiesMap.keySet().toArray(new String[indianCitiesMap.size()]);
		AutoCompleteTextView txtState = (AutoCompleteTextView)findViewById(R.id.txt_state);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,states);
		txtState.setThreshold(1);
		txtState.setAdapter(adapter);
	}

	private boolean validateForm()
	{
		boolean validForm=false;
		EditText className = (EditText)findViewById(R.id.txt_name);
		EditText address = (EditText)findViewById(R.id.txt_address);
		EditText area = (EditText)findViewById(R.id.txt_area);
		EditText city = (EditText)findViewById(R.id.txt_city);
		EditText state = (EditText)findViewById(R.id.txt_state);
		EditText pincode = (EditText)findViewById(R.id.txt_pincode);

		if( className.getText().toString().length() == 0 ) {
			className.setError("Class name is required!");
			return validForm;
		}
		if( address.getText().toString().length() == 0 ) {
			address.setError("Address is required!");
			return validForm;
		}
		if( area.getText().toString().length() == 0 ) {
			area.setError("Area is required!");
			return validForm;
		}
		if( city.getText().toString().length() == 0 ) {
			city.setError("City is required!");
			return validForm;
		}
		if( state.getText().toString().length() == 0 ) {
			state.setError("State is required!");
			return validForm;
		}
		if( pincode.getText().toString().length() == 0 ) {
			pincode.setError("Pincode is required!");
			return validForm;
		}
		validForm=true;
		return validForm;
	}

	private void convertListToMap(List<KidosPartnersIndianCitiesBean> list)
	{
		for (KidosPartnersIndianCitiesBean i : list) indianCitiesMap.put(i.getState(),i);
	}
}
