package com.androidapp.kidospartners;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.androidapp.kidospartners.abstracts.KidosPartnersPrePostProcessor;
import com.androidapp.kidospartners.adpter.KidosPartnersActivityConfigurationStepsAdapter;
import com.androidapp.kidospartners.beans.KidosPartnersActivityConfigurationStepsBean;
import com.androidapp.kidospartners.utils.KidosPartnersConstants;
import com.androidapp.kidospartners.utils.KidosPartnersUtil;
import com.squareup.picasso.Picasso;

public class KidosPartnersConfigureActivityMain extends KidosPartnersPrePostProcessor {


	private static final int CONFIGURE_CLASS_DETAILS = 0;
	KidosPartnersActivityConfigurationStepsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_partners_configure_main);

		
		KidosPartnerspreProcessor();
		
		//Set Title
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(KidosPartnersUtil.setTitleText(this,KidosPartnersConstants.CONFIGURE_ACTIVITY_MAIN_SCREEN_TITLE , KidosPartnersConstants.TITLE_TEXT_FONTFACE));

		

		ImageView image=(ImageView)findViewById(R.id.img_sctivity_configuration_image);
		if(category!=null)
		{
			Picasso.with(this).load(category.getCatbackground()).fit().into(image);
		}
		else
		{
			Picasso.with(this).load("https://s3-us-west-2.amazonaws.com/kidosbucket/5.jpg").fit().into(image);
		}

		//Generate List
		adapter=new KidosPartnersActivityConfigurationStepsAdapter(this, android.R.layout.simple_list_item_1, KidosPartnersActivityConfigurationStepsBean.getActivityConfigurationSteps(),activityID);

		ListView listview= (ListView)findViewById(R.id.lst_activitysteps);
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

           

			@Override
            public void onItemClick(AdapterView<?>  l, View v, int position, long id) {
            	
				
				switch(position) 
				{
				
				case 0:
					
					Intent classActivity=new Intent(KidosPartnersConfigureActivityMain.this, KidosPartnersClassDetails.class);
					KidosPartnersPostProcessor(classActivity);
					startActivityForResult(classActivity,CONFIGURE_CLASS_DETAILS);
					break;
				case 1:
					
					Intent contactActivity=new Intent(KidosPartnersConfigureActivityMain.this, KidosPartnersContactDetails.class);
					KidosPartnersPostProcessor(contactActivity);
					startActivity(contactActivity);
					break;
				case 2:
					
					Intent activityDetailsActivity=new Intent(KidosPartnersConfigureActivityMain.this, KidosPartnersActivityDetails.class);
					KidosPartnersPostProcessor(activityDetailsActivity);
					startActivity(activityDetailsActivity);
					break;
					
					
				case 3:
					Intent locationDetailsActivity=new Intent(KidosPartnersConfigureActivityMain.this, KidosPartnersLocationDetails.class);
					KidosPartnersPostProcessor(locationDetailsActivity);
					startActivity(locationDetailsActivity);
					break;
				case 4: 
					
					Intent addImagesActivity=new Intent(KidosPartnersConfigureActivityMain.this, KidosPartnersAddImages.class);
					KidosPartnersPostProcessor(addImagesActivity);
					startActivity(addImagesActivity);
					break;
				}
            }

        });

		//VerifyActivityConfigurationStepsState();
	}

	@Override
	public void onActivityResult(int requestCode,int resultCode, Intent data) {
		System.out.println("onActivityResult:: requestCode="+requestCode+",resultCode="+resultCode);
		if (requestCode == CONFIGURE_CLASS_DETAILS && data !=null) {
		System.out.println("about to set activityID in intent: "+data.getStringExtra("activityID"));
			if(data.getStringExtra("activityID")!=null) {
				setActivityID(data.getStringExtra("activityID"));
				//Enable list items
				//Generate List
				adapter=new KidosPartnersActivityConfigurationStepsAdapter(this, android.R.layout.simple_list_item_1, KidosPartnersActivityConfigurationStepsBean.getActivityConfigurationSteps(),activityID);

				ListView listview= (ListView)findViewById(R.id.lst_activitysteps);
				listview.setAdapter(adapter);

			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(
				R.menu.kidos_partners_configure_activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		return super.onOptionsItemSelected(item);
	}

	private void VerifyActivityConfigurationStepsState() {
		ListView listview = (ListView) findViewById(R.id.lst_activitysteps);

		System.out.println("*!@# In VerifyActivityConfigurationStepsState..listview.getChildCount()="+listview.getChildCount()+",activityID="+activityID);
		//disable listitems (except first one) if activityID is null
		if (activityID == null) {

			for (int i = 1; i < listview.getChildCount(); i++) {
				listview.getChildAt(i).setEnabled(false);
			}
		}
		else {
			for (int i = 1; i < listview.getChildCount(); i++) {
				listview.getChildAt(i).setEnabled(true);
			}

		}
	}


}
