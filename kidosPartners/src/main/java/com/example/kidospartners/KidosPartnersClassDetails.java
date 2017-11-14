package com.example.kidospartners;

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

import com.example.kidospartners.abstracts.KidosPartnersPrePostProcessor;
import com.example.kidospartners.beans.KidosPartnersClassDetailsBean;
import com.example.kidospartners.databinding.ActivityKidosPartnersClassDetailsBinding;
import com.example.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.example.kidospartners.utils.KidosPartnersConstants;
import com.example.kidospartners.utils.KidosPartnersRestClient;
import com.example.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import static com.example.kidospartners.utils.KidosPartnersUtil.parseOutputAndTakeAction;

public class KidosPartnersClassDetails extends KidosPartnersPrePostProcessor implements IKidosPartnersRestClientWrapper {

	private Factory mFactory ;

	private String getClassDetailsURI=KidosPartnersConstants.GET_CLASSDETAILS_BY_ACTIVITYID_URI;
	private String saveClassDetailsURI=KidosPartnersConstants.SAVE_CLASSDETAILS_BY_ACTIVITYID_URI;

	ActivityKidosPartnersClassDetailsBinding classDetailsBinding;

	KidosPartnersClassDetailsBean classDetails =new KidosPartnersClassDetailsBean()	;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		KidosPartnerspreProcessor();

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
		Type type = new TypeToken<Map<String, Object>>(){}.getType();
		Map<String, Object> classDetailsMap = new Gson().fromJson(new Gson().toJson(classDetails), type);

		restRequest(KidosPartnersClassDetails.this, classDetailsMap, KidosPartnersConstants.POST, saveClassDetailsURI);


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
	}
}
