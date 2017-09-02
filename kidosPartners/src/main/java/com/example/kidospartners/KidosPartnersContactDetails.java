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
import android.widget.Toast;

import com.example.kidospartners.abstracts.KidosPartnersPrePostProcessor;
import com.example.kidospartners.beans.KidosPartnersCategoryBean;
import com.example.kidospartners.beans.KidosPartnersClassDetailsBean;
import com.example.kidospartners.beans.KidosPartnersContactDetailsBean;
import com.example.kidospartners.databinding.ActivityKidosPartnersContactDetailsBinding;
import com.example.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.example.kidospartners.utils.KidosPartnersConstants;
import com.example.kidospartners.utils.KidosPartnersRestClient;
import com.example.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class KidosPartnersContactDetails extends KidosPartnersPrePostProcessor implements IKidosPartnersRestClientWrapper {

private KidosPartnersCategoryBean categoryBean;
	
	private Factory mFactory ;

	private String getContactDetailsURI=KidosPartnersConstants.GET_CONTACTDETAILS_BY_ACTIVITYID_URI;
	private String saveContactDetailsURI=KidosPartnersConstants.SAVE_CONTACTDETAILS_BY_ACTIVITYID_URI;

	ActivityKidosPartnersContactDetailsBinding contactDetailsBinding;

	KidosPartnersContactDetailsBean contactDetails=new KidosPartnersContactDetailsBean();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		KidosPartnerspreProcessor();

		restRequest(KidosPartnersContactDetails.this, null, KidosPartnersConstants.GET, getContactDetailsURI+activitySummary.getActivityId());

		contactDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_kidos_partners_contact_details);

		mFactory= KidosPartnersUtil.getLayoutFactory(this.getApplicationContext());
		

		//Set Title
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(KidosPartnersUtil.setTitleText(this,KidosPartnersConstants.CONTACT_DETAILS_SCREEN_TITLE , KidosPartnersConstants.TITLE_TEXT_FONTFACE));

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
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId()==R.id.savecontactdetails)
			saveContactDetails();
		
		return super.onOptionsItemSelected(item);
	}

	private void saveContactDetails()
	{
		Type type = new TypeToken<Map<String, Object>>(){}.getType();
		Map<String, Object> classDetailsMap = new Gson().fromJson(new Gson().toJson(contactDetails), type);
		restRequest(KidosPartnersContactDetails.this, classDetailsMap, KidosPartnersConstants.POST, saveContactDetailsURI);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		new MenuInflater(getApplication()).inflate(R.menu.kidos_partners_contact_details, menu);
		return true;
	}


	@Override
	public void restRequest(IKidosPartnersRestClientWrapper context, Map<String, Object> args, String method, String url) {
		KidosPartnersRestClient.sendRequest(context,args,method,url);
	}

	@Override
	public void restCallBack(String restOutput, String requestUrl) {
		System.out.println("In Rest Call back method of KidosPartnersClassDetails::requestUrl="+requestUrl);

		Gson gson = new Gson();

		if(requestUrl.contains(getContactDetailsURI)) {


			contactDetails = gson.fromJson(restOutput, new TypeToken<KidosPartnersClassDetailsBean>() {}.getType());
			contactDetailsBinding.setContactdetails(contactDetails);
		}
		else if(requestUrl.contains(saveContactDetailsURI))
		{
			if(restOutput!=null) {
				JsonObject response = gson.fromJson(restOutput, JsonObject.class);

				Toast.makeText(KidosPartnersContactDetails.this, response.get("msg").toString(), Toast.LENGTH_SHORT).show();
				KidosPartnersContactDetails.this.finish();
			}
		}

	}
}
