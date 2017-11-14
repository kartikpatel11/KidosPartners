package com.example.kidospartners;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.kidospartners.abstracts.KidosPartnersPrePostProcessor;
import com.example.kidospartners.adpter.KidosPartnersCategoryAdapter;
import com.example.kidospartners.beans.KidosPartnersCategoryBean;
import com.example.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.example.kidospartners.utils.KidosPartnersConstants;
import com.example.kidospartners.utils.KidosPartnersRestClient;
import com.example.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KidosPartnersChooseActivity extends KidosPartnersPrePostProcessor implements IKidosPartnersRestClientWrapper{

	 private String getCategoriesUrl=KidosPartnersConstants.GET_CATEGORIES_URI;
	 private List<KidosPartnersCategoryBean> data=new ArrayList<KidosPartnersCategoryBean>();
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_partners_choose);

		KidosPartnerspreProcessor();

		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(KidosPartnersUtil.setTitleText(this,KidosPartnersConstants.CHOOSE_ACTIVITY_SCREEN_TITLE , KidosPartnersConstants.TITLE_TEXT_FONTFACE));
		
		restRequest(KidosPartnersChooseActivity.this, null, KidosPartnersConstants.GET, getCategoriesUrl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kidos_partners_choose, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void restRequest(IKidosPartnersRestClientWrapper context,
			Map<String, Object> args, String method, String url) {
		KidosPartnersRestClient.sendRequest(context,args,method,url);
		
	}

	@Override
	public void restCallBack(String restOutput, String requestUrl) {
		System.out.println("In Rest Call back method of KidosPartnersChooseActivity");
		Gson gson=new Gson();
		
		data=gson.fromJson(restOutput,new TypeToken<List<KidosPartnersCategoryBean>>(){}.getType());
		
		ListView categoryListView = (ListView) findViewById(R.id.lst_categorynames);

		KidosPartnersCategoryAdapter adapter = new KidosPartnersCategoryAdapter(this,android.R.layout.simple_list_item_1,data);
		categoryListView.setAdapter(adapter);
		
		categoryListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?>  l, View v, int position, long id) {
            	KidosPartnersCategoryBean categoryBean=data.get(position);
            	Intent activity=new Intent(KidosPartnersChooseActivity.this,KidosPartnersConfigureActivityMain.class);
				KidosPartnersPostProcessor(activity);
				activity.putExtra("category",new Gson().toJson(categoryBean));
            	startActivity(activity);
            }

        });

	}

	

}
