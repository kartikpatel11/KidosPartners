package com.example.kidospartners.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

public class KidosPartnersUtil {

	public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;


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

	
}
