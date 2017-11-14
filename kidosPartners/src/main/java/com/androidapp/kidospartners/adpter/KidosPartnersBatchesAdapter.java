package com.androidapp.kidospartners.adpter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidapp.kidospartners.R;
import com.androidapp.kidospartners.beans.KidosPartnersBatchesBean;
import com.androidapp.kidospartners.databinding.LayoutKidosPartnersBatcheslistItemBinding;

import java.util.List;

public class KidosPartnersBatchesAdapter extends ArrayAdapter<KidosPartnersBatchesBean>{

	private Context context;

	
	private static class ViewHolder {

	    private TextView days;
	    private TextView timings;
	}
	
	public KidosPartnersBatchesAdapter(Context context, int textViewResourceId, List<KidosPartnersBatchesBean> items) {
        super(context, textViewResourceId, items);
        this.context=context;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder viewHolder;

        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        LayoutKidosPartnersBatcheslistItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_kidos_partners_batcheslist_item, parent, false);


        binding.setBatchdetails(getItem(position));
        return binding.getRoot();

      /*  if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
            .inflate(R.layout.layout_kidos_partners_batcheslist_item, parent, false);


            viewHolder = new ViewHolder();
            viewHolder.days = (TextView) convertView.findViewById(R.id.txt_batch_days);
            viewHolder.timings = (TextView) convertView.findViewById(R.id.txt_batch_timings);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        KidosPartnersBatchesBean item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
                // do whatever you want with your string and long
            viewHolder.days.setText(item.getDays().toString().replace("[", "").replace("]", ""));
            viewHolder.timings.setText(item.getStarttime()+" - "+item.getEndtime());
      
        }
        
        

        return convertView;*/
    }

}
