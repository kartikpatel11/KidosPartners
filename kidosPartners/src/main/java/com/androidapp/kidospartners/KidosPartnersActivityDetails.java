package com.androidapp.kidospartners;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.androidapp.kidospartners.abstracts.KidosPartnersPrePostProcessor;
import com.androidapp.kidospartners.adpter.KidosPartnersBatchesAdapter;
import com.androidapp.kidospartners.beans.KidosPartnersActivityDetailsBean;
import com.androidapp.kidospartners.beans.KidosPartnersBatchesBean;
import com.androidapp.kidospartners.databinding.ActivityKidosPartnersActivityDetailsBinding;
import com.androidapp.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.androidapp.kidospartners.utils.KidosPartnersConstants;
import com.androidapp.kidospartners.utils.KidosPartnersRestClient;
import com.androidapp.kidospartners.utils.KidosPartnersScheduleDialogFragment;
import com.androidapp.kidospartners.utils.KidosPartnersScheduleDialogFragment.KidosPartnersScheduleDialogListener;
import com.androidapp.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KidosPartnersActivityDetails extends KidosPartnersPrePostProcessor implements KidosPartnersScheduleDialogListener,IKidosPartnersRestClientWrapper {

    private String getActivityDetailsURI=KidosPartnersConstants.GET_ACTIVITYDETAILS_BY_ACTIVITYID_URI;
    private String saveActivityDetailsURI=KidosPartnersConstants.SAVE_ACTIVITYDETAILS_BY_ACTIVITYID_URI;

	private String errorMsg = "Batch details can not be empty";

    ActivityKidosPartnersActivityDetailsBinding activityDetailsBinding;

    KidosPartnersActivityDetailsBean activityDetails=new KidosPartnersActivityDetailsBean();


	 private List<KidosPartnersBatchesBean> batchesData=new ArrayList<KidosPartnersBatchesBean>();
	 KidosPartnersBatchesAdapter adapter ;
	 private Factory mFactory ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_kidos_partners_activity_details);
		mFactory= KidosPartnersUtil.getLayoutFactory(this.getApplicationContext());

        KidosPartnerspreProcessor();

        restRequest(KidosPartnersActivityDetails.this, null, KidosPartnersConstants.GET, getActivityDetailsURI+getActivityID());

        activityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_kidos_partners_activity_details);


        //Set Title
				ActionBar actionBar = getSupportActionBar();
				actionBar.setTitle(KidosPartnersUtil.setTitleText(this,KidosPartnersConstants.ACTIVITY_DETAILS_SCREEN_TITLE , KidosPartnersConstants.TITLE_TEXT_FONTFACE));
				
			Button insertBatch=(Button)findViewById(R.id.btn_insertbatch);
			insertBatch.setOnClickListener(new InsertBatchOnClickListener());


			
	}
	
	@Override
	public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

		if(name.contains("android.support.v7.internal.view.menu.ActionMenuItemView")) {
	        LayoutInflater li = LayoutInflater.from(context);
	        View view = null;
	        try {
	            view = li.createView(name, null, attrs);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        if (view != null) {
	            if(mFactory != null) {
	                view = mFactory.onCreateView(name,context,attrs);
	            }
	            return view;
	        }
	    }
	    return super.onCreateView(name, context, attrs);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		new MenuInflater(getApplication()).inflate(R.menu.kidos_partners_activity_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==R.id.saveactivitydetails) {
			saveActivityDetails();
			//this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

	private void saveActivityDetails() {
		if (validateForm()) {
			Type type = new TypeToken<Map<String, Object>>() {}.getType();
			System.out.println("new Gson().toJson(activityDetails)=" + new Gson().toJson(activityDetails));

			Map<String, Object> activityDetailsMap = new Gson().fromJson(new Gson().toJson(activityDetails), type);
			restRequest(KidosPartnersActivityDetails.this, activityDetailsMap, KidosPartnersConstants.POSTJSON, saveActivityDetailsURI);

		}
	}

	// The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
    	
    	
    	TimePicker fromTime=(TimePicker)(dialog.getDialog().findViewById(R.id.fromTimePicker));
    	TimePicker toTime=(TimePicker)(dialog.getDialog().findViewById(R.id.toTimePicker));
    	
    	ToggleButton mon = (ToggleButton)(dialog.getDialog().findViewById(R.id.toggleMon));
    	ToggleButton tue = (ToggleButton)(dialog.getDialog().findViewById(R.id.toggleTue));
    	ToggleButton wed = (ToggleButton)(dialog.getDialog().findViewById(R.id.toggleWed));
    	ToggleButton thu = (ToggleButton)(dialog.getDialog().findViewById(R.id.toggleThu));
    	ToggleButton fri = (ToggleButton)(dialog.getDialog().findViewById(R.id.toggleFri));
    	ToggleButton sat = (ToggleButton)(dialog.getDialog().findViewById(R.id.toggleSat));
    	ToggleButton sun = (ToggleButton)(dialog.getDialog().findViewById(R.id.toggleSun));
    	
    	LinkedList<Boolean> days=new LinkedList<Boolean>();
    	days.add(mon.isChecked());
    	days.add(tue.isChecked());
    	days.add(wed.isChecked());
    	days.add(thu.isChecked());
    	days.add(fri.isChecked());
    	days.add(sat.isChecked());
    	days.add(sun.isChecked());
    	
    	String fromTimeStr = String.format("%02d:%02d", fromTime.getCurrentHour(), fromTime.getCurrentMinute()); //fromTime.getCurrentHour() + ":" + fromTime.getCurrentMinute();
    	String toTimeStr = String.format("%02d:%02d", toTime.getCurrentHour(), toTime.getCurrentMinute()); //toTime.getCurrentHour() + ":" + toTime.getCurrentMinute();

    	KidosPartnersBatchesBean bean=new KidosPartnersBatchesBean(days, fromTimeStr, toTimeStr);
    	
    	adapter.add(bean);
    	
    	dialog.dismiss();
        
    	
    	
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        
    }
	
	class InsertBatchOnClickListener implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			showNoticeDialog();
		}
		
	}
	
    private void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new KidosPartnersScheduleDialogFragment();
        dialog.show(getSupportFragmentManager(), "KidosPartnersScheduleDialogFragment");
    }

	@Override
	public void restRequest(IKidosPartnersRestClientWrapper context,
			Map<String, Object> args, String method, String url) {
        KidosPartnersRestClient.sendRequest(context,args,method,url);
		
	}

	@Override
	public void restCallBack(String restOutput, String requestUrl) {

        System.out.println("In Rest Call back method of KidosPartnersActivityDetails::requestUrl="+requestUrl);

        Gson gson = new Gson();

        if(requestUrl.contains(getActivityDetailsURI)) {


            activityDetails = gson.fromJson(restOutput, new TypeToken<KidosPartnersActivityDetailsBean>() {}.getType());

            activityDetailsBinding.setActivitydetails(activityDetails);


			//Attach data to listview adapter
			batchesData=activityDetails.getBatches();
			System.out.println("-->batchesData="+batchesData);
			ListView batchesListView = (ListView) findViewById(R.id.lst_batches);

			adapter = new KidosPartnersBatchesAdapter(this,android.R.layout.simple_list_item_1,batchesData);
			batchesListView.setAdapter(adapter);


		}
		else if(requestUrl.contains(saveActivityDetailsURI))
		{
			if(restOutput!=null) {
				JsonObject response = gson.fromJson(restOutput, JsonObject.class);

				Toast.makeText(KidosPartnersActivityDetails.this, response.get("msg").toString(), Toast.LENGTH_SHORT).show();
				KidosPartnersActivityDetails.this.finish();
			}
		}
		
	}

	private boolean validateForm()
	{
		boolean validForm=false;
		EditText desc = (EditText)findViewById(R.id.txt_description);
		EditText fees = (EditText)findViewById(R.id.txt_fees);
		EditText minAge = (EditText)findViewById(R.id.txt_minage);
		EditText maxAge = (EditText)findViewById(R.id.txt_maxage);
		EditText state = (EditText)findViewById(R.id.txt_state);
		EditText pincode = (EditText)findViewById(R.id.txt_pincode);

		if( desc.getText().toString().length() == 0 ) {
			desc.setError("Description is required!");
			return validForm;
		}
		if( fees.getText().toString().length() == 0 ) {
			fees.setError("Fees is required!");
			return validForm;
		}

		if(adapter.isEmpty()) {
			KidosPartnersUtil.createDialog(KidosPartnersActivityDetails.this, KidosPartnersUtil.TITLE_ERROR, errorMsg, KidosPartnersUtil.NONE_DIALOG, null);
			return validForm;
		}

		validForm=true;
		return validForm;
	}




}
