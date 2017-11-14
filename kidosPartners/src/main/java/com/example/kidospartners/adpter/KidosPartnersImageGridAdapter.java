package com.example.kidospartners.adpter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.kidospartners.R;
import com.example.kidospartners.beans.KidosPartnersImageDetailsBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class KidosPartnersImageGridAdapter extends ArrayAdapter<KidosPartnersImageDetailsBean>{

	Context context; 
	int layoutResourceId; 
	List<KidosPartnersImageDetailsBean> data = new ArrayList<KidosPartnersImageDetailsBean>();
	
	public KidosPartnersImageGridAdapter(Context context, int layoutResourceId, List<KidosPartnersImageDetailsBean> data)
	{ 
		super(context, layoutResourceId, data); 
		this.layoutResourceId = layoutResourceId; 
		this.context = context; 
		this.data = data; 
	}
	
	@Override 
	public View getView(int position, View convertView, ViewGroup parent) 
	{ 
		View row = convertView; 
		RecordHolder holder = null; 
		if (row == null) 
		{ 
			LayoutInflater inflater = ((Activity) context).getLayoutInflater(); 
			row = inflater.inflate(layoutResourceId, parent, false); 
			
			holder = new RecordHolder(); 
			//holder.txtTitle = (TextView) row.findViewById(R.id.item_text); 
			holder.imageItem = (ImageView) row.findViewById(R.id.item_image); 
			row.setTag(holder); 
		} 
		else 
		{ 
			holder = (RecordHolder) row.getTag(); 
		} 
		
		KidosPartnersImageDetailsBean item = data.get(position);
		Picasso.with(context).load(item.getImgurl()).fit().into(holder.imageItem);
	//	holder.txtTitle.setText(item.getName()); 
		 
		return row; 
	} 
	
	static class RecordHolder 
	{ 
		//TextView txtTitle; 
		ImageView imageItem; 
	}

	

	
}
