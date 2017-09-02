package com.example.kidospartners.adpter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kidospartners.R;
import com.example.kidospartners.beans.KidosPartnersActivitySummaryBean;

public class KidosPartnersActivitySummaryAdapter extends ArrayAdapter<KidosPartnersActivitySummaryBean>{

	private Context context;
    private static class ViewHolder {
    private TextView activityName;
    private TextView activityArea;
    private TextView activityId;
}

public KidosPartnersActivitySummaryAdapter(Context context, int textViewResourceId, List<KidosPartnersActivitySummaryBean> items) {
    super(context, textViewResourceId, items);
    this.context=context;
}

public View getView(int position, View convertView, ViewGroup parent) {
	ViewHolder viewHolder;
    if (convertView == null) {
        convertView = LayoutInflater.from(this.getContext())
        .inflate(R.layout.layout_kidos_partners_activitysummarylist_item, parent, false);

        viewHolder = new ViewHolder();
        viewHolder.activityName = (TextView) convertView.findViewById(R.id.txt_activity_name);
        viewHolder.activityArea = (TextView) convertView.findViewById(R.id.txt_activity_area);
        viewHolder.activityId = (TextView) convertView.findViewById(R.id.txt_activity_summary_id);

        convertView.setTag(viewHolder);
    } else {
        viewHolder = (ViewHolder) convertView.getTag();
    }

    KidosPartnersActivitySummaryBean item = getItem(position);
    if (item!= null) {
        viewHolder.activityName.setText(item.getName());
        viewHolder.activityArea.setText(item.getArea());
        viewHolder.activityId.setText(item.getActivityId());
    }
       return convertView;
}

}
