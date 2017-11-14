package com.androidapp.kidospartners;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.androidapp.kidospartners.beans.KidosPartnersRegistrationDetailsBean;
import com.androidapp.kidospartners.beans.KidosPartnersUserBean;
import com.androidapp.kidospartners.databinding.ActivityKidosPartnersRegistrationBinding;
import com.androidapp.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.androidapp.kidospartners.utils.KidosPartnersConstants;
import com.androidapp.kidospartners.utils.KidosPartnersRestClient;
import com.androidapp.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;


import static com.androidapp.kidospartners.utils.KidosPartnersUtil.hideSoftKeyboard;

public class KidosPartnersRegistration extends AppCompatActivity implements IKidosPartnersRestClientWrapper {

	private String registerUserUri=KidosPartnersConstants.REGISTER_USER_URI;
	ActivityKidosPartnersRegistrationBinding registrationBinding;

	KidosPartnersRegistrationDetailsBean registrationdetails =new KidosPartnersRegistrationDetailsBean()	;
	private KidosPartnersUserBean userDetails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_partners_registration);

		setupUI(findViewById(R.id.layout_registration));

		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(KidosPartnersUtil.setTitleText(this,KidosPartnersConstants.REGISTRATION_SCREEN_TITLE , KidosPartnersConstants.TITLE_TEXT_FONTFACE));

		registrationBinding = DataBindingUtil.setContentView(this, R.layout.activity_kidos_partners_registration);

		registrationBinding.setRegistrationdetails(registrationdetails);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kidos_partners_registration, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		return super.onOptionsItemSelected(item);
	}

	public void Register(View v) {

		if(validateForm()) {
			Type type = new TypeToken<Map<String, Object>>() {
			}.getType();
			Map<String, Object> registrationDetailsMap = new Gson().fromJson(new Gson().toJson(registrationdetails), type);
			restRequest(KidosPartnersRegistration.this, registrationDetailsMap, KidosPartnersConstants.POST, registerUserUri);
		}
		//Intent intent=new Intent(this,KidosPartnersWelcome.class);
		//this.startActivity(intent);
	}

	private boolean validateForm()
	{
		boolean validForm = false;

		EditText firstName = (EditText)findViewById(R.id.txt_first_name);
		EditText lastName = (EditText)findViewById(R.id.txt_last_name);
		EditText phNo = (EditText)findViewById(R.id.txt_phno);
		EditText email = (EditText)findViewById(R.id.txt_emailid);
		EditText pass = (EditText)findViewById(R.id.txt_password);
		EditText repass = (EditText)findViewById(R.id.txt_retypepass);

		if( firstName.getText().toString().length() == 0 ) {
			firstName.setError("First name is required!");
			return validForm;
		}

		if( lastName.getText().toString().length() == 0 ) {
			lastName.setError("Last name is required!");
			return validForm;
		}

		if( phNo.getText().toString().length() == 0 ) {
			phNo.setError("Phone number is required!");
			return validForm;
		}

		if( email.getText().toString().length() == 0 ) {
			email.setError("Email is required!");
			return validForm;
		}

		if( pass.getText().toString().length() == 0 ) {
			pass.setError("Password is required!");
			return validForm;
		}

		if( repass.getText().toString().length() == 0 ) {
			repass.setError("Re-type Password is required!");
			return validForm;
		}

		if( pass.getText().toString().length() != 0 && !pass.getText().toString().equals(repass.getText().toString())) {
			repass.setError("Password and re-type password should match!");
			return validForm;
		}

		validForm=true;
		return validForm;
	}

	@Override
	public void restRequest(IKidosPartnersRestClientWrapper context, Map<String, Object> args, String method, String url) {
		KidosPartnersRestClient.sendRequest(context,args,method,url);
	}

	@Override
	public void restCallBack(String restOutput, String requestUrl) {
		JsonObject response=null;
		System.out.println("In Rest Call back method of KidosPartnersRegistration::"+restOutput);

		Gson gson = new Gson();
		if(restOutput!=null) {
			response = gson.fromJson(restOutput, JsonObject.class);
		}

		if(response!=null && response.get("errmsg")==null)
		{
			userDetails = gson.fromJson(restOutput, new TypeToken<KidosPartnersUserBean>() {}.getType());

			Toast.makeText(KidosPartnersRegistration.this, "Congratulations!! Successfully registered.", Toast.LENGTH_LONG).show();

			Intent activity = new Intent(KidosPartnersRegistration.this, KidosPartnersWelcome.class);
			activity.putExtra("user", gson.toJson(userDetails));
			startActivity(activity);
		}
		else
		{
			Toast.makeText(KidosPartnersRegistration.this, response.get("errmsg").toString(), Toast.LENGTH_LONG).show();
		}

	}

	public void setupUI(View view) {

		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText)) {
			view.setOnTouchListener(new View.OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					hideSoftKeyboard(KidosPartnersRegistration.this);
					return false;
				}
			});
		}

		//If a layout container, iterate over children and seed recursion.
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				View innerView = ((ViewGroup) view).getChildAt(i);
				setupUI(innerView);
			}
		}
	}
}
