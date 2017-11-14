package com.androidapp.kidospartners.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.androidapp.kidospartners.R;

@SuppressLint("ResourceAsColor")
public class KidosPartnersScheduleDialogFragment extends DialogFragment {

		public interface KidosPartnersScheduleDialogListener {
	        public void onDialogPositiveClick(DialogFragment dialog);
	        public void onDialogNegativeClick(DialogFragment dialog);
	    }

	    // Use this instance of the interface to deliver action events
		KidosPartnersScheduleDialogListener mListener;

	    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
	    @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        // Verify that the host activity implements the callback interface
	        try {
	            // Instantiate the NoticeDialogListener so we can send events to the host
	            mListener = (KidosPartnersScheduleDialogListener) activity;
	        } catch (ClassCastException e) {
	            // The activity doesn't implement the interface, throw exception
	            throw new ClassCastException(activity.toString()
	                    + " must implement NoticeDialogListener");
	        }
	    }
	    
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        
	        LayoutInflater inflater = getActivity().getLayoutInflater();

	        View view = inflater.inflate(R.layout.layout_kidos_partners_add_activity_schedule, null);
	        
	        // Inflate and set the layout for the dialog
	        // Pass null as the parent view because its going in the dialog layout
	        builder.setView(view)
	        .setTitle("Batch Schedule")
	        .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   mListener.onDialogNegativeClick(KidosPartnersScheduleDialogFragment.this);
	                   }
	               })
	        .setPositiveButton(R.string.btn_addbatch, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   mListener.onDialogPositiveClick(KidosPartnersScheduleDialogFragment.this);
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
	    
	    @SuppressLint("NewApi")
		@Override
	    public void onStart() {
	        super.onStart();

	        Resources res = getResources();

	        int blue = Color.parseColor("#1E90FF");
	        
	        //Title
	        int titleId = res.getIdentifier("alertTitle", "id", "android");
	        View title = getDialog().findViewById(titleId);
	        if (title != null) {
	            ((TextView) title).setTextColor(blue);
	            ((TextView)title).setGravity(Gravity.CENTER_HORIZONTAL);
	        }
	        
	      
	    }

	    
	
	
}
