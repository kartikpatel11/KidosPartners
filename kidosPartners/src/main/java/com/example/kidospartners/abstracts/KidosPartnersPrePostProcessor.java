package com.example.kidospartners.abstracts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.kidospartners.beans.KidosPartnersActivitySummaryBean;
import com.example.kidospartners.beans.KidosPartnersCategoryBean;
import com.example.kidospartners.beans.KidosPartnersUserBean;
import com.example.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;

public class KidosPartnersPrePostProcessor extends AppCompatActivity {
	
	protected KidosPartnersUserBean user;
	protected KidosPartnersCategoryBean category;
	protected KidosPartnersActivitySummaryBean activitySummary;
	
	protected KidosPartnersUserBean getUserDetails()
	{
		return (KidosPartnersUserBean)KidosPartnersUtil.getBundledObject(getIntent().getExtras(),"user",KidosPartnersUserBean.class);
	}
	
	protected KidosPartnersCategoryBean getCategoryDetails()
	{
		 return (KidosPartnersCategoryBean)KidosPartnersUtil.getBundledObject(getIntent().getExtras(),"category",KidosPartnersCategoryBean.class);
		
	}
	
	protected  KidosPartnersActivitySummaryBean getActivitySummary()
	{
		return (KidosPartnersActivitySummaryBean)KidosPartnersUtil.getBundledObject(getIntent().getExtras(),"activitysummary",KidosPartnersActivitySummaryBean.class);
	}
	
	protected void KidosPartnerspreProcessor()
	{
		user=getUserDetails();
		activitySummary = getActivitySummary();
		category=getCategoryDetails();
	}
	
	protected Intent KidosPartnersPostProcessor(Intent intent)
	{
		if(user!=null)
			intent.putExtra("user",new Gson().toJson(user));
		
		if(category!=null)
			intent.putExtra("category",new Gson().toJson(category));
		
		if(activitySummary!=null)
			intent.putExtra("activitysummary",new Gson().toJson(activitySummary));
		
		return intent;
	}


}
