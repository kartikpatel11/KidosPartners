package com.example.kidospartners.adpter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.kidospartners.R;
import com.example.kidospartners.beans.KidosPartnersImageBean;
import com.squareup.picasso.Picasso;

public class KidosPartnersImageGridAdapter extends ArrayAdapter<KidosPartnersImageBean>{

	Context context; 
	int layoutResourceId; 
	ArrayList<KidosPartnersImageBean> data = new ArrayList<KidosPartnersImageBean>(); 
	
	public KidosPartnersImageGridAdapter(Context context, int layoutResourceId, ArrayList<KidosPartnersImageBean> data) 
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
		
		KidosPartnersImageBean item = data.get(position); 
		Picasso.with(context).load(item.getImgUrl()).fit().into(holder.imageItem);
	//	holder.txtTitle.setText(item.getName()); 
		 
		return row; 
	} 
	
	static class RecordHolder 
	{ 
		//TextView txtTitle; 
		ImageView imageItem; 
	}

	

	
}
