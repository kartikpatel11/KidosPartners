package com.androidapp.kidospartners.adpter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidapp.kidospartners.R;
import com.androidapp.kidospartners.beans.KidosPartnersActivitySummaryBean;
import com.androidapp.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.androidapp.kidospartners.utils.KidosPartnersConstants;
import com.androidapp.kidospartners.utils.KidosPartnersRestClient;
import com.androidapp.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KidosPartnersActivitySummaryAdapter extends ArrayAdapter<KidosPartnersActivitySummaryBean> implements IKidosPartnersRestClientWrapper {

    private String updateActivityStateUrl= KidosPartnersConstants.UPDATE_ACTIVITY_STATE;

    private Context context;
    private Activity activity;
    ProgressDialog dialog ;


    private static class ViewHolder {
        private TextView activityName;
        private TextView activityArea;
        private TextView activityId;
        private SwitchCompat isPublished;
    }

    public KidosPartnersActivitySummaryAdapter(Context context, Activity _activity,int textViewResourceId, List<KidosPartnersActivitySummaryBean> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.activity = _activity;

        dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.layout_kidos_partners_activitysummarylist_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.activityName = (TextView) convertView.findViewById(R.id.txt_activity_name);
            viewHolder.activityArea = (TextView) convertView.findViewById(R.id.txt_activity_area);
            viewHolder.activityId = (TextView) convertView.findViewById(R.id.txt_activity_summary_id);
            viewHolder.isPublished = (SwitchCompat) convertView.findViewById(R.id.switch_published);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final KidosPartnersActivitySummaryBean item = getItem(position);
        if (item != null) {
            viewHolder.activityName.setText(item.getName());
            viewHolder.activityArea.setText(item.getArea());
            viewHolder.activityId.setText(item.getActivityId());
            viewHolder.isPublished.setChecked(item.isPublished());


            viewHolder.isPublished.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    System.out.println("-----OnCheckedChangeListener called...");
                    HashMap<String, Object> argMap=new HashMap<>();


                   if(viewHolder.isPublished.isPressed() && isChecked==true) {
                       KidosPartnersActivitySummaryBean i=getItem(position);
                       if(!(i.isActivitydetails() & i.isContactdetails() & i.isLocationdetails())) {
                           //createErrorDialog(i.getSetupMissingMsg().toString()).show();
                           KidosPartnersUtil.createDialog(activity, KidosPartnersUtil.TITLE_ERROR, i.getSetupMissingMsg().toString(), KidosPartnersUtil.NONE_DIALOG, null);
                           viewHolder.isPublished.setChecked(false);
                       }
                       else {

                           dialog.setMessage("Please wait...Publishing the activity");
                           dialog.show();
                           argMap.put("published",isChecked);
                           argMap.put("activityId", item.getActivityId());
                           restRequest(KidosPartnersActivitySummaryAdapter.this, argMap, KidosPartnersConstants.POSTJSON, updateActivityStateUrl);

                       }

                   } else if(viewHolder.isPublished.isPressed() && isChecked==false){

                       dialog.setMessage("Please wait...Unpublishing the activity");
                       dialog.show();
                       argMap.put("published",isChecked);
                       argMap.put("activityId", item.getActivityId());
                       restRequest(KidosPartnersActivitySummaryAdapter.this, argMap, KidosPartnersConstants.POSTJSON, updateActivityStateUrl);

                   }

                }
            });

        }
        return convertView;
    }


    @Override
    public void restRequest(IKidosPartnersRestClientWrapper context, Map<String, Object> args, String method, String url) {
        KidosPartnersRestClient.sendRequest(context,args,method,url);
    }

    @Override
    public void restCallBack(String restOutput, String requestUrl) {

        System.out.println("In Rest Call back method of KidosPartnersActivitySummaryAdapter:"+requestUrl);
        Gson gson = new Gson();

        if(requestUrl.contains(updateActivityStateUrl))
        {
            if(restOutput!=null) {
                JsonObject response = gson.fromJson(restOutput, JsonObject.class);

                Toast.makeText(activity, response.get("msg").toString(), Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(activity, "Something went wrong..Please try again", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }

    }



}


