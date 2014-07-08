package info.androidhive.tabsswipe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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

class MyCustomAdapter extends ArrayAdapter<Item> {

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
		cartIds = ((deliveryclient) activity.getApplication()).getMyCartIds();
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
					// TODO Auto-generated method stub
					// OrdersFragment.move();
				}
			});
			name = (TextView) convertView.findViewById(R.id.name);
			name.setText(item.getName());
			price = (TextView) convertView.findViewById(R.id.price);
			price.setText("" + item.getPrice());
			plus = (ImageView) convertView.findViewById(R.id.plus);
			minus = (ImageView) convertView.findViewById(R.id.minus);
			qtTxt = (TextView) convertView.findViewById(R.id.qtTxt);

			int pId = item.getId();
			Product cp = new Product(pId);
			int pp = ((deliveryclient) activity.getApplication()).getMyCart()
					.getProductPosition(cp);
			if (pp >= 0) {
				qtTxt.setText(""
						+ ((deliveryclient) activity.getApplication())
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
					// TODO Auto-generated method stub
					// OrdersFragment.move();
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
			int pp = ((deliveryclient) activity.getApplication()).getMyCart()
					.getProductPosition(cp);
			if (pp >= 0) {
				qtTxt.setText(""
						+ ((deliveryclient) activity.getApplication())
								.getMyCart().getProductCount(cp));
				totalprice.setText(""
						+ ((deliveryclient) activity.getApplication())
								.getMyCart().getProductCount(cp)
						* item.getPrice());
			}
		}
	}

	public void addToCart(Item item) {

		Product p = new Product(item.getId());
		p.setPrice(item.getPrice());
		p.setName(item.getName());
		Fragment f = ((deliveryclient) activity.getApplication())
				.getCurrentFragment();
		String cn = null;
		if (f != null) {
			cn = f.getClass().getName();
		}

		((deliveryclient) activity.getApplication()).getMyCart().addToCart(cn,
				p);

	}

	public void rmvFromCart(Item item) {
		Product p = new Product(item.getId());
		p.setPrice(item.getPrice());
		Fragment f = ((deliveryclient) activity.getApplication())
				.getCurrentFragment();
		String cn = null;
		if (f != null) {
			cn = f.getClass().getName();
		}
		((deliveryclient) activity.getApplication()).getMyCart().rmvFromCart(
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

	class radioHolder extends ViewHolder {
		TextView name;

		public radioHolder(View convertView, Item item) {
			name = (TextView) convertView.findViewById(R.id.name);
			name.setText(item.getName());
		}
	}

	class txImgHolder extends ViewHolder {
		TextView name;
		ImageView picture;

		public txImgHolder(View convertView, Item item) {
			TextView name = (TextView) convertView.findViewById(R.id.name);
			ImageView picture = (ImageView) convertView
					.findViewById(R.id.image);
			picture.setImageResource(item.getImg());
			name.setText(item.getName());
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		Item currentItem = currentList.get(position);

		if (convertView == null) {

			int layout = 0;
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (this.type.equals("txt") || this.type.equals("empty")) {
				layout = R.layout.row_txt;
				convertView = vi.inflate(layout, null);
				holder = new txtHolder(convertView, currentItem);
			} else if (this.type.equals("txtImg")) {

				layout = R.layout.row_txtimg;
				convertView = vi.inflate(layout, null);
				holder = new txImgHolder(convertView, currentItem);

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

				layout = R.layout.row_radiobutton;
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

		return convertView;
	}

}