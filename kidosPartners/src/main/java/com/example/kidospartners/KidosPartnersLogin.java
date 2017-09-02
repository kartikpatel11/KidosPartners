package com.example.kidospartners;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kidospartners.beans.KidosPartnersUserBean;
import com.example.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.example.kidospartners.utils.KidosPartnersConstants;
import com.example.kidospartners.utils.KidosPartnersRestClient;
import com.example.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

public class KidosPartnersLogin extends AppCompatActivity implements IKidosPartnersRestClientWrapper{

	private String kidosPartnersLoginUrl=KidosPartnersConstants.KIDOSPARTNERS_LOGIN_URI;
	private KidosPartnersUserBean data;
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_partners_login);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(KidosPartnersUtil.setTitleText(this,KidosPartnersConstants.LOGIN_SCREEN_TITLE , KidosPartnersConstants.TITLE_TEXT_FONTFACE));
			
		TextView registerLabel=(TextView)findViewById(R.id.label_register);
		registerLabel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(v.getContext(),KidosPartnersRegistration.class);
				v.getContext().startActivity(intent);
				
			}
		});
		
		
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
		
		String mobile = ((EditText)findViewById(R.id.txt_login_phno)).getText().toString();
		String pass = ((EditText)findViewById(R.id.txt_password)).getText().toString();
		
		//Arg map
		Map<String,Object> argMap=new HashMap<String,Object>();
		argMap.put("mobile", mobile);
		argMap.put("pass", pass);
		
		//restRequest(KidosPartnersLogin.this, argMap, KidosPartnersConstants.GET, kidosPartnersLoginUrl);
		
		//Dummy
		KidosPartnersUserBean dummyUserObj = new KidosPartnersUserBean();
		dummyUserObj.setUserid(7);
		dummyUserObj.setEmailid("patel.kartikv@gmail.com");
		dummyUserObj.setMobile("+919820742767");
		dummyUserObj.setFirstname("Kartik");
		dummyUserObj.setLastname("Patel");
		
		Intent intent=new Intent(this,KidosPartnersWelcome.class);
		intent.putExtra("user",new Gson().toJson(dummyUserObj));
		this.startActivity(intent);
		//Dummy Ends
		
		
}

	@Override
	public void restRequest(IKidosPartnersRestClientWrapper context,
			Map<String, Object> args, String method, String url) {
		KidosPartnersRestClient.sendRequest(context,args,method,url);
		
	}
	
	@Override
	public void restCallBack(String restOutput, String requestUrl) {
		System.out.println("In Rest Call back method of KidosPartnersLogin");
		
		
		if(data!=null)
		{
			Gson gson=new Gson();
			data=gson.fromJson(restOutput,new TypeToken<KidosPartnersUserBean>(){}.getType());
		
			Intent activity=new Intent(KidosPartnersLogin.this,KidosPartnersWelcome.class);
        	activity.putExtra("user",new Gson().toJson(data));
        	startActivity(activity);
        
		}
		else
		{
			createErrorDialog();
		}
		
				
	}

	
	private AlertDialog createErrorDialog() {
		return new AlertDialog.Builder(getApplicationContext())
        .setTitle("Error")
        .setMessage("Username or password is wrong!! Please retry.")
        .setCancelable(true).create();
		
	}

	

}
