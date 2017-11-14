package com.example.kidospartners.abstracts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.kidospartners.beans.KidosPartnersCategoryBean;
import com.example.kidospartners.beans.KidosPartnersUserBean;
import com.example.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;

public class KidosPartnersPrePostProcessor extends AppCompatActivity {
	
	protected KidosPartnersUserBean user;
	protected KidosPartnersCategoryBean category;
	protected String activityID;
	
	protected KidosPartnersUserBean getUserDetails()
	{
		return (KidosPartnersUserBean)KidosPartnersUtil.getBundledObject(getIntent().getExtras(),"user",KidosPartnersUserBean.class);
	}
	
	protected KidosPartnersCategoryBean getCategoryDetails()
	{
		 return (KidosPartnersCategoryBean)KidosPartnersUtil.getBundledObject(getIntent().getExtras(),"category",KidosPartnersCategoryBean.class);
		
	}
	
	protected  String getActivityID()
	{
		return getIntent().getStringExtra("activityID");
	}


	public void setActivityID(String activityId)
	{
		this.activityID=activityId;
	}

	protected void KidosPartnerspreProcessor()
	{
		System.out.println("--------- In KidosPartnerspreProcessor");
		user=getUserDetails();
		activityID = getActivityID();
		category=getCategoryDetails();

		System.out.println("User==>"+user);
		System.out.println("Category==>"+category);
		System.out.println("activityID==>"+activityID);
	}


	protected Intent KidosPartnersPostProcessor(Intent intent)
	{
		System.out.println("--------- In KidosPartnersPostProcessor");
		if(user!=null) {
			System.out.println("Setting user as "+ new Gson().toJson(user));
				intent.putExtra("user", new Gson().toJson(user));
		}
		if(category!=null) {
			System.out.println("Setting category as "+ new Gson().toJson(category));
				intent.putExtra("category", new Gson().toJson(category));
		}
		if(activityID!=null) {
			System.out.println("Setting activityID as "+ activityID);
			intent.putExtra("activityID", activityID);
		}
		return intent;
	}



}
