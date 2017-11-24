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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.androidapp.kidospartners.abstracts.KidosPartnersPrePostProcessor;
import com.androidapp.kidospartners.beans.KidosPartnersClassDetailsBean;
import com.androidapp.kidospartners.beans.KidosPartnersIndianCitiesBean;
import com.androidapp.kidospartners.beans.KidosPartnersIndianStatesBean;
import com.androidapp.kidospartners.databinding.ActivityKidosPartnersClassDetailsBinding;
import com.androidapp.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.androidapp.kidospartners.utils.KidosPartnersConstants;
import com.androidapp.kidospartners.utils.KidosPartnersRestClient;
import com.androidapp.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.androidapp.kidospartners.utils.KidosPartnersUtil.parseOutputAndTakeAction;

public class KidosPartnersClassDetails extends KidosPartnersPrePostProcessor implements IKidosPartnersRestClientWrapper {

	private Factory mFactory ;

	private String getClassDetailsURI=KidosPartnersConstants.GET_CLASSDETAILS_BY_ACTIVITYID_URI;
	private String saveClassDetailsURI=KidosPartnersConstants.SAVE_CLASSDETAILS_BY_ACTIVITYID_URI;
	private String listIndianCitiesURI = KidosPartnersConstants.LIST_INDIANCITIES_URI;


	private TreeMap<String,TreeMap<String, String[]>> indianCitiesMap = new TreeMap<String,TreeMap<String, String[]>>();
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
			Type type = new TypeToken<Map<String, Object>>() {}.getType();
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

			List<KidosPartnersIndianStatesBean> indianCities = gson.fromJson(restOutput, new TypeToken<List<KidosPartnersIndianStatesBean>>() {}.getType());

			convertStateListToMap(indianCities);

			populateStateCityAreaList();

		}
	}


	private void populateStateCityAreaList()
	{
		final String[] states = indianCitiesMap.keySet().toArray(new String[indianCitiesMap.size()]);
		final AutoCompleteTextView txtState = (AutoCompleteTextView)findViewById(R.id.txt_state);
		final AutoCompleteTextView txtCity = (AutoCompleteTextView)findViewById(R.id.txt_city);
		final AutoCompleteTextView txtArea = (AutoCompleteTextView)findViewById(R.id.txt_area);

		ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,states);
		txtState.setThreshold(1);
		txtState.setAdapter(stateAdapter);


		txtState.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int pos,
									long id) {


				String state = txtState.getText().toString();

				System.out.println("****** Inside txtState itemclicklistner: "+state);
				Map<String, String[]> cityMap = indianCitiesMap.get(state);
				String[] cities =cityMap.keySet().toArray(new String[cityMap.size()]);

				ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(KidosPartnersClassDetails.this,android.R.layout.simple_list_item_1,cities);
				txtCity.setThreshold(1);
				txtCity.setAdapter(cityAdapter);
			}
		});





		txtCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int pos,
									long id) {

				ArrayAdapter<String> cityAdapter = (ArrayAdapter<String>) txtCity.getAdapter();
				String state = txtState.getText().toString();
				String city = txtCity.getText().toString();

				System.out.println("****** Inside txtCity itemclicklistner: "+state+","+city);

				if(state!=null && city!=null && !"".equals(state) && !"".equals(city)) {

					String[] areas = indianCitiesMap.get(state).get(city);
					Arrays.sort(areas);


					ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(KidosPartnersClassDetails.this, android.R.layout.simple_list_item_1, areas);
					txtArea.setThreshold(1);
					txtArea.setAdapter(areaAdapter);
				}

			}
		});

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

	private void convertStateListToMap(List<KidosPartnersIndianStatesBean> list)
	{

		for (KidosPartnersIndianStatesBean state : list)
		{
			TreeMap<String, String[]> cityMap = new TreeMap<String, String[]>();
			for(KidosPartnersIndianCitiesBean city: state.getCities())
			{
				cityMap.put(city.getCity(),city.getAreas());
			}

			indianCitiesMap.put(state.getState(),cityMap);
		}
	}

}
