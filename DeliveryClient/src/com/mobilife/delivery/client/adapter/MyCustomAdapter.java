package com.mobilife.delivery.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilife.delivery.client.DeliveryClientApplication;
import com.mobilife.delivery.client.R;
import com.mobilife.delivery.client.model.Item;
import com.mobilife.delivery.client.model.Product;
import com.mobilife.delivery.client.utilities.ImageTask;
import com.mobilife.delivery.client.utilities.RZHelper;
import com.mobilife.delivery.client.utilities.myURL;
import com.mobilife.delivery.client.view.activity.ProductInfoActivity;

public class MyCustomAdapter extends ArrayAdapter<Item> {

	private ArrayList<Item> currentList;
	Context context;
	private String type;
	List<Integer> cartIds = new ArrayList<Integer>();
	Activity activity;

	public MyCustomAdapter(Context context, int textViewResourceId,
			ArrayList<Item> navList) {
		super(context, textViewResourceId, navList);
		this.currentList = navList;

		this.setType();

		this.context = context;
		activity = (Activity) context;
		cartIds = ((DeliveryClientApplication) activity.getApplication()).getMyCartIds();
	}

	private void setType() {
		if (currentList.size() > 0)
			this.type = this.currentList.get(0).getType();
		else
			this.type = "empty";
	}

	public ArrayList<Item> getCurrentList() {
		return currentList;
	}

	abstract class ViewHolder {

	}

	class productHolder extends ViewHolder {
		TextView name, qtTxt;
		EditText input;
		TextView price;
		ImageView plus, minus;

		public productHolder(View convertView, final Item item) {
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					return;
				}
			});
			name = (TextView) convertView.findViewById(R.id.name);
			name.setText(item.getName());
			name.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(activity.getBaseContext(),
							ProductInfoActivity.class);
					((DeliveryClientApplication) activity.getApplication()).setProductId(item.getId());
					int catId = ((DeliveryClientApplication) activity.getApplication()).getDepthVal();
					i.putExtra("catId", catId);
					activity.startActivity(i);
				}
			});
			price = (TextView) convertView.findViewById(R.id.price);
			price.setText("" + item.getPrice());
			plus = (ImageView) convertView.findViewById(R.id.plus);
			minus = (ImageView) convertView.findViewById(R.id.minus);
			qtTxt = (TextView) convertView.findViewById(R.id.qtTxt);

			int pId = item.getId();
			Product cp = new Product(pId);
			int pp = ((DeliveryClientApplication) activity.getApplication()).getMyCart()
					.getProductPosition(cp);
			if (pp >= 0) {
				qtTxt.setText(""
						+ ((DeliveryClientApplication) activity.getApplication())
								.getMyCart().getProductCount(cp));
			}
			plus.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					int qt = Integer.parseInt(qtTxt.getText().toString());
					if (qt < 9) {
						qt++;
						qtTxt.setText("" + qt);
						addToCart(item);
					}

				}
			});
			minus.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					int qt = Integer.parseInt(qtTxt.getText().toString());
					if (qt > 0) {
						qt--;
						qtTxt.setText("" + qt);
						rmvFromCart(item);
					}
				}
			});
		}
	}

	class previewHolder extends ViewHolder {
		TextView name, qtTxt;
		TextView input;
		TextView price, totalprice;
		ImageView plus, minus;

		public previewHolder(View convertView, final Item item) {
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
				}
			});
			name = (TextView) convertView.findViewById(R.id.name);
			name.setText(item.getName());
			price = (TextView) convertView.findViewById(R.id.price);
			price.setText("" + item.getPrice());
			qtTxt = (TextView) convertView.findViewById(R.id.qtTxt);
			totalprice = (TextView) convertView.findViewById(R.id.totalprice);

			int pId = item.getId();
			Product cp = new Product(pId);
			int pp = ((DeliveryClientApplication) activity.getApplication()).getMyCart()
					.getProductPosition(cp);
			if (pp >= 0) {
				qtTxt.setText(""
						+ ((DeliveryClientApplication) activity.getApplication())
								.getMyCart().getProductCount(cp));
				totalprice.setText(""
						+ ((DeliveryClientApplication) activity.getApplication())
								.getMyCart().getProductCount(cp)
						* item.getPrice());
			}
		}
	}

	public void addToCart(Item item) {

		Product p = new Product(item.getId());
		p.setPrice(item.getPrice());
		p.setName(item.getName());
		Fragment f = ((DeliveryClientApplication) activity.getApplication())
				.getCurrentFragment();
		String cn = null;
		if (f != null) {
			cn = f.getClass().getName();
		}

		((DeliveryClientApplication) activity.getApplication()).getMyCart().addToCart(cn,
				p);

	}

	public void rmvFromCart(Item item) {
		Product p = new Product(item.getId());
		p.setPrice(item.getPrice());
		Fragment f = ((DeliveryClientApplication) activity.getApplication())
				.getCurrentFragment();
		String cn = null;
		if (f != null) {
			cn = f.getClass().getName();
		}
		((DeliveryClientApplication) activity.getApplication()).getMyCart().rmvFromCart(
				cn, p);
	}

	class orderHolder extends ViewHolder {

		TextView name;
		TextView quantity;
		TextView price;
	}

	class cartHolder extends ViewHolder {
		TextView name;
		EditText input;
		TextView unit;
		TextView price;
	}

	class txtHolder extends ViewHolder {
		TextView name;

		public txtHolder(View convertView, Item item) {
			name = (TextView) convertView.findViewById(R.id.name);
			name.setText(item.getName());
		}
	}

	class shopHolder extends ViewHolder {
		TextView name;
		public shopHolder(View convertView, Item item) {
			name = (TextView) convertView.findViewById(R.id.name);
			name.setText(item.getName());
		}
	}

	class radioHolder extends ViewHolder {
		TextView name;
		Button makeDefault;
		public radioHolder(View convertView, final Item item) {
			name = (TextView) convertView.findViewById(R.id.name);
			makeDefault = (Button) convertView.findViewById(R.id.makeDefault);
			if(item.isDefault())
			{
				makeDefault.setVisibility(View.GONE);
			}
			else
			{				
				makeDefault.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						String serverURL = new myURL("set_default", "customers/addresses", item.getId(), 0).getURL();
						RZHelper p = new RZHelper(serverURL, activity, "nothing", true);
						p.put(null);
					}
				});
				
			}
			name.setText(item.getName());
		}
	}

	class txImgHolder extends ViewHolder {
		TextView name;
		ImageView picture;
		public txImgHolder(View convertView, Item item) {
			TextView name = (TextView) convertView.findViewById(R.id.name);
			ImageView picture = (ImageView) convertView.findViewById(R.id.image);
			picture.setImageResource(item.getImg());
			name.setText(item.getName());
		}
	}
	
	class txUrlImgHolder extends ViewHolder {
		TextView name;
		ImageView picture;
		public txUrlImgHolder(View convertView, Item item,Activity activity) {
			try{
				TextView name = (TextView) convertView.findViewById(R.id.name);
				if(item.getPhotoName()!=null){
					ImageView picture = (ImageView) convertView.findViewById(R.id.image);
					new ImageTask(picture,activity).execute(item.getPhotoName());
				}
				else
				{
					ImageView picture = (ImageView) convertView.findViewById(R.id.image);
					new ImageTask(picture,activity).execute("empty");
				}
				name.setText(item.getName());
			}catch(Exception e){

			}
		}
	}

	class branchHolder extends ViewHolder {
		TextView name,time,minimum,charge;
		ImageView picture;
		public branchHolder(View convertView, Item item) {
			name = (TextView) convertView.findViewById(R.id.name);
			name.setText(item.getName());
			time = (TextView) convertView.findViewById(R.id.estimatedtime);
			time.setText(""+item.getTime());
			minimum = (TextView) convertView.findViewById(R.id.minorder);
			minimum.setText(""+item.getMinimum());
			charge = (TextView) convertView.findViewById(R.id.delivcharge);
			charge.setText(""+item.getCharge());
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		Item currentItem = currentList.get(position);

		if (convertView == null) {
			int layout = 0;
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			if (this.type.equals("txt") || this.type.equals("empty")) {
				layout = R.layout.row_txt;
				convertView = vi.inflate(layout, null);
				holder = new txtHolder(convertView, currentItem);
			} else if (this.type.equals("txtImg") ) {
				layout = R.layout.row_txtimg;
				convertView = vi.inflate(layout, null);
				holder = new txUrlImgHolder(convertView, currentItem,activity);
			} else if (this.type.equals("shop") ) {
				layout = R.layout.row_shop;
				convertView = vi.inflate(layout, null);
				holder = new txUrlImgHolder(convertView, currentItem,activity);
			} else if (this.type.equals("businesses") ) {
				layout = R.layout.row_business;
				convertView = vi.inflate(layout, null);
				holder = new txUrlImgHolder(convertView, currentItem,activity);
			}  else if (this.type.equals("branch") ) {
				layout = R.layout.row_branch;
				convertView = vi.inflate(layout, null);
				holder = new branchHolder(convertView, currentItem);
			} else if (this.type.equals("product")) {
				layout = R.layout.row_product;
				convertView = vi.inflate(layout, null);
				holder = new productHolder(convertView, currentItem);
			} else if (this.type.equals("order")) {
				layout = R.layout.activity_main;
				holder = new orderHolder();
			} else if (this.type.equals("cart")) {
				layout = R.layout.row_product;
				convertView = vi.inflate(layout, null);
				holder = new productHolder(convertView, currentItem);
			} else if (this.type.equals("address")) {
				layout = R.layout.row_address;
				convertView = vi.inflate(layout, null);
				holder = new radioHolder(convertView, currentItem);
			} else if (this.type.equals("preview")) {
				layout = R.layout.row_preview;
				convertView = vi.inflate(layout, null);
				holder = new previewHolder(convertView, currentItem);
			} else {
				Log.d("rays", "nothing");
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			
		}
		Item navitem = currentList.get(position);
		if (this.type.equals("txt") || this.type.equals("empty")) {
			holder = new txtHolder(convertView, navitem);
		} else if (this.type.equals("txtImg") ) {
			holder = new txUrlImgHolder(convertView, navitem,activity);
		} else if (this.type.equals("shop") ) {
			holder = new txUrlImgHolder(convertView, navitem,activity);
		} else if (this.type.equals("businesses") ) {
			holder = new txUrlImgHolder(convertView, navitem,activity);
		}  else if (this.type.equals("branch") ) {
			holder = new branchHolder(convertView, navitem);
		} else if (this.type.equals("product")) {
			holder = new productHolder(convertView, navitem);
		} else if (this.type.equals("order")) {
			holder = new orderHolder();
		} else if (this.type.equals("cart")) {
			holder = new productHolder(convertView, navitem);
		} else if (this.type.equals("address")) {
			holder = new radioHolder(convertView, navitem);
		} else if (this.type.equals("preview")) {
			holder = new previewHolder(convertView, navitem);
		}
		return convertView;
	}

}