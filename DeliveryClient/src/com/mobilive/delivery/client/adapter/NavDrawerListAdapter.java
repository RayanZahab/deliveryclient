package com.mobilive.delivery.client.adapter;

import java.util.ArrayList;

import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.utilities.NavDrawerItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class NavDrawerListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	
	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	private class ViewHolder {
		ToggleButton name;
		TextView txtTitle, txtCount;
		ImageView imgIcon;
		TextView price;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
            
            ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
            TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
            holder.imgIcon = imgIcon;
            holder.txtTitle = txtTitle;
            holder.txtCount = txtCount;
            convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.imgIcon.setImageResource(navDrawerItems.get(position).getIcon());        
		holder.txtTitle.setText(navDrawerItems.get(position).getTitle());
        
        // displaying count
        // check whether it set visible or not
        if(navDrawerItems.get(position).getCounterVisibility()){
        	holder.txtCount.setText(navDrawerItems.get(position).getCount());
        }else{
        	// hide the counter view
        	holder.txtCount.setVisibility(View.GONE);
        }
        
        return convertView;
	}

}
