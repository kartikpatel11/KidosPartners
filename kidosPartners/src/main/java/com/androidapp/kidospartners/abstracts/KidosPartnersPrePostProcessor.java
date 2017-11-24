package com.androidapp.kidospartners.abstracts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.androidapp.kidospartners.KidosPartnersInternetConnectionIssue;
import com.androidapp.kidospartners.beans.KidosPartnersCategoryBean;
import com.androidapp.kidospartners.beans.KidosPartnersUserBean;
import com.androidapp.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;

public class KidosPartnersPrePostProcessor extends AppCompatActivity {

	protected KidosPartnersUserBean user;
	protected KidosPartnersCategoryBean category;
	protected String activityID;


	protected KidosPartnersUserBean getUserDetails() {
		return (KidosPartnersUserBean) KidosPartnersUtil.getBundledObject(getIntent().getExtras(), "user", KidosPartnersUserBean.class);
	}

	protected KidosPartnersCategoryBean getCategoryDetails() {
		return (KidosPartnersCategoryBean) KidosPartnersUtil.getBundledObject(getIntent().getExtras(), "category", KidosPartnersCategoryBean.class);

	}

	protected String getActivityID() {
		return getIntent().getStringExtra("activityID");
	}


	public void setActivityID(String activityId) {
		this.activityID = activityId;
	}

	protected void KidosPartnerspreProcessor() {
		System.out.println("--------- In KidosPartnerspreProcessor");
		user = getUserDetails();
		activityID = getActivityID();
		category = getCategoryDetails();

		System.out.println("User==>" + user);
		System.out.println("Category==>" + category);
		System.out.println("activityID==>" + activityID);
	}


	protected Intent KidosPartnersPostProcessor(Intent intent) {
		System.out.println("--------- In KidosPartnersPostProcessor");
		if (user != null) {
			System.out.println("Setting user as " + new Gson().toJson(user));
			intent.putExtra("user", new Gson().toJson(user));
		}
		if (category != null) {
			System.out.println("Setting category as " + new Gson().toJson(category));
			intent.putExtra("category", new Gson().toJson(category));
		}
		if (activityID != null) {
			System.out.println("Setting activityID as " + activityID);
			intent.putExtra("activityID", activityID);
		}
		return intent;
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (v instanceof EditText) {
				Rect outRect = new Rect();
				v.getGlobalVisibleRect(outRect);
				if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
					v.clearFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
		}
		return super.dispatchTouchEvent(event);
	}


	@Override
	protected void onResume() {
		super.onResume();
		checkInternetConnection();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkInternetConnection();
	}

	private void checkInternetConnection(){
		if (!KidosPartnersUtil.isNetworkAvailable(this)) {
			Intent intent =new Intent(this.getApplicationContext(),KidosPartnersInternetConnectionIssue.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.getApplicationContext().startActivity(intent);
		}
	}

}