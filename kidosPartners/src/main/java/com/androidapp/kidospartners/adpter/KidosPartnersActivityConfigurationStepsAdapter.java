package com.androidapp.kidospartners.adpter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidapp.kidospartners.R;
import com.androidapp.kidospartners.beans.KidosPartnersActivityConfigurationStepsBean;

public class KidosPartnersActivityConfigurationStepsAdapter extends ArrayAdapter<KidosPartnersActivityConfigurationStepsBean>{

	private Context context;
    private static class ViewHolder {
    private TextView stepTitle;
    //private ImageView categoryImage;
    private TextView stepDescription;
}

public KidosPartnersActivityConfigurationStepsAdapter(Context context, int textViewResourceId, List<KidosPartnersActivityConfigurationStepsBean> items) {
    super(context, textViewResourceId, items);
    this.context=context;
}

public View getView(int position, View convertView, ViewGroup parent) {
	ViewHolder viewHolder;
    if (convertView == null) {
        convertView = LayoutInflater.from(this.getContext())
        .inflate(R.layout.layout_kidos_partners_configureactivitylist_item, parent, false);

        viewHolder = new ViewHolder();
        viewHolder.stepTitle = (TextView) convertView.findViewById(R.id.txt_activity_configure_step_title);
        viewHolder.stepDescription = (TextView) convertView.findViewById(R.id.txt_activity_configure_step_detail);
     //   viewHolder.categoryImage = (ImageView) convertView.findViewById(R.id.img_category_image);

        convertView.setTag(viewHolder);
    } else {
        viewHolder = (ViewHolder) convertView.getTag();
    }

    KidosPartnersActivityConfigurationStepsBean item = getItem(position);
    if (item!= null) {
        // My layout has only one TextView
            // do whatever you want with your string and long
        viewHolder.stepTitle.setText(item.getStepTitle());
        viewHolder.stepDescription.setText(item.getStepDescription());
   //     Picasso.with(context).load(item.getCatImg()).fit().into(viewHolder.categoryImage);
    }

    return convertView;
}
}
