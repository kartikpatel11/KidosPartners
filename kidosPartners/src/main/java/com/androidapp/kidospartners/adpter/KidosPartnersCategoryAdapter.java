package com.androidapp.kidospartners.adpter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.kidospartners.R;
import com.androidapp.kidospartners.beans.KidosPartnersCategoryBean;

public class KidosPartnersCategoryAdapter extends ArrayAdapter<KidosPartnersCategoryBean>{
	
		private Context context;
		    private static class ViewHolder {
	        private TextView categoryName;
	        private ImageView categoryImage;
	    }

	    public KidosPartnersCategoryAdapter(Context context, int textViewResourceId, List<KidosPartnersCategoryBean> items) {
	        super(context, textViewResourceId, items);
	        this.context=context;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	    	ViewHolder viewHolder;
	        if (convertView == null) {
	            convertView = LayoutInflater.from(this.getContext())
	            .inflate(R.layout.layout_kidos_partners_categorylist_item, parent, false);

	            viewHolder = new ViewHolder();
	            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.txt_category_name);
	         //   viewHolder.categoryImage = (ImageView) convertView.findViewById(R.id.img_category_image);

	            convertView.setTag(viewHolder);
	        } else {
	            viewHolder = (ViewHolder) convertView.getTag();
	        }

	        KidosPartnersCategoryBean item = getItem(position);
	        if (item!= null) {
	            // My layout has only one TextView
	                // do whatever you want with your string and long
	            viewHolder.categoryName.setText(item.getCatName());
	       //     Picasso.with(context).load(item.getCatImg()).fit().into(viewHolder.categoryImage);
	        }
	        
	        

	        return convertView;
	    }
}


