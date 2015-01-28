package info.androidhive.tabsswipe;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OrdersAdapter extends ArrayAdapter<Item> {

	private ArrayList<Item> orderList;
	Context context;
	View convertView;
	public boolean empty =false;

	public OrdersAdapter(Context context, int textViewResourceId,
			ArrayList<Item> navList) {
		super(context, textViewResourceId, navList);
		this.orderList = new ArrayList<Item>();
		this.orderList.addAll(navList);
		this.context = context;
	}

	class ViewHolder {
		TextView address;
		TextView numbofitems;
		TextView totalamount;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			holder = new ViewHolder();
			if(!empty)
			{
				convertView = vi.inflate(R.layout.row_order, null);				
	
				holder.address = (TextView) convertView
						.findViewById(R.id.useraddress);
				holder.numbofitems = (TextView) convertView
						.findViewById(R.id.numbofitems);
				holder.totalamount = (TextView) convertView
						.findViewById(R.id.totalamount);
			}
			else
			{
				Log.d("ray","empty adapt");
				convertView = vi.inflate(R.layout.categories_list, null);
				holder.address = (TextView) convertView.findViewById(R.id.name);
				holder.address.setText(orderList.get(0).getTitle());
			}
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
			this.convertView = convertView;
		}
		if(!empty)
		{
			Item orderitem = orderList.get(position);
	
			holder.address.setText(orderitem.getTitle());
			holder.address.setTag(orderitem);
	
			holder.numbofitems.setText(new GlobalM().getago(orderitem.getDate()));
			holder.numbofitems.setTag(orderitem);
	
			holder.totalamount.setText(orderitem.getTotalPrice() + " L.L");
			holder.totalamount.setTag(orderitem);
		}
		return convertView;
	}
}
