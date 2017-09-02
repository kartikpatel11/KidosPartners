package com.example.kidospartners;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kidospartners.abstracts.KidosPartnersPrePostProcessor;
import com.example.kidospartners.adpter.KidosPartnersActivitySummaryAdapter;
import com.example.kidospartners.beans.KidosPartnersActivitySummaryBean;
import com.example.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.example.kidospartners.utils.KidosPartnersConstants;
import com.example.kidospartners.utils.KidosPartnersRestClient;
import com.example.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KidosPartnersWelcome extends KidosPartnersPrePostProcessor implements IKidosPartnersRestClientWrapper{

	 private String getActivitySummaryByUser=KidosPartnersConstants.GET_ACTIVITY_SUMMARY_BY_USERID_URI;
	 private List<KidosPartnersActivitySummaryBean> data=new ArrayList<KidosPartnersActivitySummaryBean>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_partners_welcome);
		
		KidosPartnerspreProcessor();
		
		
		restRequest(KidosPartnersWelcome.this, null, KidosPartnersConstants.GET, getActivitySummaryByUser+user.getUserid());
	
		
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(KidosPartnersUtil.setTitleText(this,KidosPartnersConstants.WELCOME_SCREEN_TITLE , KidosPartnersConstants.TITLE_TEXT_FONTFACE));
		
		
		
		
		TextView registerActivityLabel = (TextView)findViewById(R.id.label_registeractivity);
		registerActivityLabel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				configureActivity(v);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kidos_partners_welcome, menu);
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

	private void configureActivity(View v) {
		Intent intent=new Intent(this,KidosPartnersChooseActivity.class);
		this.startActivity(intent);
}

	@Override
	public void restRequest(IKidosPartnersRestClientWrapper context,
			Map<String, Object> args, String method, String url) {
		
		KidosPartnersRestClient.sendRequest(context,args,method,url);
		
	}

	@Override
	public void restCallBack(String restOutput, String requestUrl) {
	
		System.out.println("In Rest Call back method of KidosPartnersWelcome");
		Gson gson=new Gson();
		
		data=gson.fromJson(restOutput,new TypeToken<List<KidosPartnersActivitySummaryBean>>(){}.getType());
		
		if(data!=null && data.size()>0)
		{
			((TextView)findViewById(R.id.label_noactivity1)).setVisibility(View.INVISIBLE);
			((TextView)findViewById(R.id.label_activitypresent1)).setVisibility(View.VISIBLE);
		}
		
		ListView activitySummaryView = (ListView) findViewById(R.id.lst_activitysummary);

		KidosPartnersActivitySummaryAdapter adapter = new KidosPartnersActivitySummaryAdapter(this,android.R.layout.simple_list_item_1,data);
		activitySummaryView.setAdapter(adapter);
		
		activitySummaryView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?>  l, View v, int position, long id) {
            	KidosPartnersActivitySummaryBean activitySummaryBean=data.get(position);
            	Intent activity=new Intent(KidosPartnersWelcome.this,KidosPartnersConfigureActivityMain.class);
            	activity.putExtra("activitysummary",new Gson().toJson(activitySummaryBean));
            	startActivity(activity);
            	
            }

        });
	}

}
