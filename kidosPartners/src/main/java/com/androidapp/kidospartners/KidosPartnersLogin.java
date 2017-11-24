package com.androidapp.kidospartners;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.androidapp.kidospartners.abstracts.KidosPartnersPrePostProcessor;
import com.androidapp.kidospartners.beans.KidosPartnersUserBean;
import com.androidapp.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.androidapp.kidospartners.utils.KidosPartnersConstants;
import com.androidapp.kidospartners.utils.KidosPartnersCryptoUtils;
import com.androidapp.kidospartners.utils.KidosPartnersRestClient;
import com.androidapp.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

public class KidosPartnersLogin extends KidosPartnersPrePostProcessor implements IKidosPartnersRestClientWrapper{

	private String kidosPartnersLoginUrl=KidosPartnersConstants.KIDOSPARTNERS_LOGIN_URI;
	private KidosPartnersUserBean data;
	private static SharedPreferences kidosPartnersPreferences;
	public static final String PREFS_NAME = "KidosPartnersPrefsFile";
	private boolean hasLoggedIn = false;
	private String errorMsg = "Mobile number or password is wrong!! Please retry";



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		kidosPartnersPreferences= getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

		if(!hasUserLoggedIn()) {
			setContentView(R.layout.activity_kidos_partners_login);

			ActionBar actionBar = getSupportActionBar();
			actionBar.setTitle(KidosPartnersUtil.setTitleText(this, KidosPartnersConstants.LOGIN_SCREEN_TITLE, KidosPartnersConstants.TITLE_TEXT_FONTFACE));

			TextView registerLabel = (TextView) findViewById(R.id.label_register);
			registerLabel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), KidosPartnersRegistration.class);
					v.getContext().startActivity(intent);

				}
			});

			TextView forgotPassLabel = (TextView) findViewById(R.id.label_forgotpass);
			forgotPassLabel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(view.getContext(), KidosPartnersForgotPassword.class);
					view.getContext().startActivity(intent);
				}
			});
		}
		else //directly go to welcome screen
		{
			Intent activity=new Intent(KidosPartnersLogin.this,KidosPartnersWelcome.class);
			activity.putExtra("user",retrieveFromKidosPartnersPreference("user"));
			startActivity(activity);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kidos_partner_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		return super.onOptionsItemSelected(item);
	}
	
	public void Login(View v) {

		if(validateForm()) {
			String mobile = ((EditText) findViewById(R.id.txt_login_phno)).getText().toString();
			String pass = ((EditText) findViewById(R.id.txt_password)).getText().toString();

			//Arg map
			Map<String, Object> argMap = new HashMap<String, Object>();
			argMap.put("mobile", mobile);
			argMap.put("pass", KidosPartnersCryptoUtils.encodeData(pass));

			restRequest(KidosPartnersLogin.this, argMap, KidosPartnersConstants.POST, kidosPartnersLoginUrl);

		}
}

	@Override
	public void restRequest(IKidosPartnersRestClientWrapper context,
			Map<String, Object> args, String method, String url) {
		KidosPartnersRestClient.sendRequest(context,args,method,url);
		
	}
	
	@Override
	public void restCallBack(String restOutput, String requestUrl) {
		System.out.println("In Rest Call back method of KidosPartnersLogin:: restOutput="+restOutput);
		
		
		if(!"null".equals(restOutput))
		{
			System.out.println("Rest Output is not 'null'");
			Gson gson=new Gson();
			data=gson.fromJson(restOutput,new TypeToken<KidosPartnersUserBean>(){}.getType());
		
			Intent activity=new Intent(KidosPartnersLogin.this,KidosPartnersWelcome.class);
        	activity.putExtra("user",new Gson().toJson(data));

			CheckBox signedInChkBox = (CheckBox)findViewById(R.id.chk_keepsignedin);
			if(signedInChkBox.isChecked())
			{
				//Also put user attribute in sharedpreferences for future logins
				storeToKidosPartnersPreference("user",new Gson().toJson(data));
				storeToKidosPartnersPreference("hasLoggedIn", true);
			}

			startActivity(activity);
        
		}
		else
		{
			System.out.println("About to raise error");
			KidosPartnersUtil.createDialog(KidosPartnersLogin.this, KidosPartnersUtil.TITLE_ERROR, errorMsg, KidosPartnersUtil.NONE_DIALOG, null);

		}
		
				
	}

	private boolean validateForm()
	{
		boolean validForm=false;
		EditText phNo = (EditText)findViewById(R.id.txt_login_phno);
		EditText pass = (EditText)findViewById(R.id.txt_password);

		if( phNo.getText().toString().length() == 0 ) {
			phNo.setError("Mobile number is required!");
			return validForm;
		}
		if( pass.getText().toString().length() == 0 ) {
			pass.setError("Password is required!");
			return validForm;
		}
		validForm=true;
		return validForm;
	}

	public static void storeToKidosPartnersPreference(String key,String val)
	{
		SharedPreferences.Editor editor = kidosPartnersPreferences.edit();

		//Set "hasLoggedIn" to true
		editor.putString(key, val);

		// Commit the edits!
		editor.commit();
	}

	public static void storeToKidosPartnersPreference(String key,boolean val)
	{
		SharedPreferences.Editor editor = kidosPartnersPreferences.edit();

		//Set "hasLoggedIn" to true
		editor.putBoolean(key, val);

		// Commit the edits!
		editor.commit();
	}


	public static String  retrieveFromKidosPartnersPreference(String key)
	{
		String val=kidosPartnersPreferences.getString(key, null);
		return val;
	}

	private boolean hasUserLoggedIn()
	{
		//Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
		hasLoggedIn = kidosPartnersPreferences.getBoolean("hasLoggedIn", false);
		return hasLoggedIn;
	}

}
