package com.androidapp.kidospartners.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidapp.kidospartners.R;
import com.androidapp.kidospartners.beans.KidosPartnersActivityConfigurationStepsBean;

import java.util.List;

public class KidosPartnersActivityConfigurationStepsAdapter extends ArrayAdapter<KidosPartnersActivityConfigurationStepsBean>{

	private Context context;
    private String activityID;
    private static class ViewHolder {
    private TextView stepTitle;
    //private ImageView categoryImage;
    private TextView stepDescription;
}

public KidosPartnersActivityConfigurationStepsAdapter(Context context, int textViewResourceId, List<KidosPartnersActivityConfigurationStepsBean> items, String _activityID) {
    super(context, textViewResourceId, items);
    this.context=context;
    this.activityID = _activityID;
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
        viewHolder.stepTitle.setText(item.getStepTitle());
        viewHolder.stepDescription.setText(item.getStepDescription());
   //     Picasso.with(context).load(item.getCatImg()).fit().into(viewHolder.categoryImage);

    }

    if(!isEnabled(position))
    {
        convertView.setAlpha(0.5F);
    }

    return convertView;
    }

    @Override
    public boolean isEnabled(int position) {

        if(this.activityID==null)
        {
            if(position>0) {
                return false;
            }
            else {
                return true;
            }
        }
        else
        {
            return true;
        }

    }
}
