package com.example.kidospartners;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.example.kidospartners.abstracts.KidosPartnersPrePostProcessor;
import com.example.kidospartners.adpter.KidosPartnersBatchesAdapter;
import com.example.kidospartners.beans.KidosPartnersBatchesBean;
import com.example.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.example.kidospartners.utils.KidosPartnersConstants;
import com.example.kidospartners.utils.KidosPartnersScheduleDialogFragment;
import com.example.kidospartners.utils.KidosPartnersScheduleDialogFragment.KidosPartnersScheduleDialogListener;
import com.example.kidospartners.utils.KidosPartnersUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KidosPartnersActivityDetails extends KidosPartnersPrePostProcessor implements KidosPartnersScheduleDialogListener,IKidosPartnersRestClientWrapper {

	// private String getActivityDetailsUrl=KidosPartnersConstants.GET_CATEGORIES_URI;
	 private List<KidosPartnersBatchesBean> data=new ArrayList<KidosPartnersBatchesBean>();
	 KidosPartnersBatchesAdapter adapter ;
	 private Factory mFactory ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_partners_activity_details);
		mFactory= KidosPartnersUtil.getLayoutFactory(this.getApplicationContext());
		
		//Set Title
				ActionBar actionBar = getSupportActionBar();
				actionBar.setTitle(KidosPartnersUtil.setTitleText(this,KidosPartnersConstants.ACTIVITY_DETAILS_SCREEN_TITLE , KidosPartnersConstants.TITLE_TEXT_FONTFACE));
				
			Button insertBatch=(Button)findViewById(R.id.btn_insertbatch);
			insertBatch.setOnClickListener(new InsertBatchOnClickListener());

			ListView categoryListView = (ListView) findViewById(R.id.lst_batches);

			adapter = new KidosPartnersBatchesAdapter(this,android.R.layout.simple_list_item_1,data);
			categoryListView.setAdapter(adapter);

			
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
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		return super.onOptionsItemSelected(item);
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
    	
    	String fromTimeStr = fromTime.getCurrentHour() + ":" + fromTime.getCurrentMinute();
    	String toTimeStr = toTime.getCurrentHour() + ":" + toTime.getCurrentMinute();

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restCallBack(String restOutput, String requestUrl) {
		// TODO Auto-generated method stub
		
	}
	

}
