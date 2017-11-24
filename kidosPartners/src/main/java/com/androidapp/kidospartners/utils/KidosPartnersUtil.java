package com.androidapp.kidospartners.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import static android.app.Activity.RESULT_OK;

public class KidosPartnersUtil {

	public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
	public static final String TITLE_ERROR = "Error";
	public static final String TITLE_SUCCESS = "Success";
	public static final String NEUTRAL_DIALOG = "Neutral";
	public static final String YESNO_DIALOG = "YesNo";
	public static final String NONE_DIALOG = "None";




	public static SpannableString setTitleText(Context context_,String title_, String fontFace_)
	{
		SpannableString s = new SpannableString(title_);
		s.setSpan(new KidosPartnersTypefaceSpan(context_, fontFace_), 0, s.length(),
		        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return s;
	}

	public static <T> Object getBundledObject(Bundle extras, String key,
			Class<T> class_) {
		//get CategoryBean
				if (extras != null) {
					String jsonStr = extras.getString(key);
					if(jsonStr!=null)
					{
						 return new Gson().fromJson(jsonStr, class_);
					}
				}
				return null;
		
	}

	public static Factory getLayoutFactory(Context context) {
		 return new LayoutInflater.Factory() {
	        @Override
	        public View onCreateView(String name, final Context context, AttributeSet attrs) {
	                try {
	                    LayoutInflater li = LayoutInflater.from(context);
	                    final View view = li.createView(name, null, attrs);

	                    new Handler().post(new Runnable() {
	                        @Override
	                        public void run() {
	                        	Typeface face = Typeface.createFromAsset(
	                                    context.getAssets(),"Roboto-Thin.ttf"); 
	                            ((TextView) view).setTypeface(face);
	                            ((TextView) view).setTextSize(18);

	                        }
	                    });

	                    return view;

	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            return null;
	        }
		};
	}
	
	  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	   public static boolean checkPermission(final Context context)
	   {
	       int currentAPIVersion = Build.VERSION.SDK_INT;
	       if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
	       {
	           if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
	               if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
	                   AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
	                   alertBuilder.setCancelable(true);
	                   alertBuilder.setTitle("Permission necessary");
	                   alertBuilder.setMessage("External storage permission is necessary");
	                   alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	                       @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	                       public void onClick(DialogInterface dialog, int which) {
	                           ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
	                       }
	                   });
	                   AlertDialog alert = alertBuilder.create();
	                   alert.show();

	               } else {
	                   ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
	               }
	               return false;
	           } else {
	               return true;
	           }
	       } else {
	           return true;
	       }
	   }


	public static boolean isNetworkAvailable(Activity activity) {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}


	public static void parseOutputAndTakeAction(String restOutput,Activity activity) {

		JsonObject response=null;
		Gson gson = new Gson();


		if(restOutput!=null) {
			response = gson.fromJson(restOutput, JsonObject.class);

			if(response.get("errmsg")!=null) {
				Toast.makeText(activity, response.get("errmsg").toString(), Toast.LENGTH_SHORT).show();
			}
			else {

				if(response.get("activityId")!=null) {
					System.out.println("------New Activity ID="+response.get("activityId").getAsString());
					String activityId = response.get("activityId").getAsString();

					Intent i = new Intent();
					i.putExtra("activityID", activityId);
					activity.setResult(RESULT_OK, i);
				}
				Toast.makeText(activity, response.get("msg").toString(), Toast.LENGTH_SHORT).show();
				activity.finish();
			}

		}
		else
		{
			Toast.makeText(activity, "Sorry, Something horribly went wrong!!", Toast.LENGTH_SHORT).show();
		}

	}


	public static void createDialog(Activity activity, String titleStr, String msgStr, String dialogBoxType, DialogInterface.OnClickListener listener) {

		AlertDialog.Builder popupBuilder = new AlertDialog.Builder(activity);

		//title
		TextView title = new TextView(activity);
		title.setText(titleStr);
		title.setBackgroundColor(Color.BLACK);
		title.setPadding(20, 20, 20, 20);
		title.setGravity(Gravity.CENTER);
		title.setTextColor(Color.WHITE);
		title.setTextSize(20);

		popupBuilder.setCustomTitle(title);

		//body
		TextView myMsg = new TextView(activity);
		myMsg.setText(msgStr);
		myMsg.setTextColor(Color.BLACK);
		myMsg.setPadding(20,20,20,20);
		myMsg.setTextSize(15f);
		myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
		popupBuilder.setView(myMsg);

		if(NEUTRAL_DIALOG.equals(dialogBoxType)) {
			popupBuilder.setNeutralButton("OK", listener);
		}
		else if(YESNO_DIALOG.equals(dialogBoxType)) {
			popupBuilder.setPositiveButton("Yes",listener).setNegativeButton("No",listener);
		}
		else  {
			popupBuilder.setCancelable(true);
		}

		AlertDialog alert = popupBuilder.create();
		alert.show();

		if(NEUTRAL_DIALOG.equals(dialogBoxType))
		{
			final Button neutralButton = alert.getButton(AlertDialog.BUTTON_NEUTRAL);
			LinearLayout.LayoutParams neutralButtonLL = (LinearLayout.LayoutParams) neutralButton.getLayoutParams();
			neutralButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
			neutralButton.setLayoutParams(neutralButtonLL);

		}

	}


}
