package info.androidhive.tabsswipe;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

class MyCustomAdapter extends ArrayAdapter<Item> {

	private ArrayList<Item> currentList;
	Context context;
	private String type;

	public MyCustomAdapter(Context context, int textViewResourceId,
			ArrayList<Item> navList) {
		super(context, textViewResourceId, navList);
		this.currentList = new ArrayList<Item>();
		this.currentList.addAll(navList);
		this.context = context;
	}

	private void setType() {
		this.type = this.currentList.get(0).getType();
	}

	public ArrayList<Item> getCurrentList() {
		return currentList;
	}

	abstract class ViewHolder {

	}

	class productHolder extends ViewHolder {
		TextView name;
		EditText input;
		TextView price;
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
	}

	class txImgHolder extends ViewHolder {
		TextView name;
		ImageView picture;
	}

	class radioHolder extends ViewHolder {
		RadioButton name;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {

			LayoutInflater vi = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// convertView = vi.inflate(R.layout.categories_list, null);
			int layout = 0;
			if (this.type.equals("txt")) {

				layout = R.layout.row_txt;
				holder = new txtHolder();

			} else if (this.type.equals("txtImg")) {

				layout = R.layout.row_txtimg;
				holder = new txImgHolder();

			} else if (this.type.equals("product")) {

				layout = R.layout.row_product;
				holder = new productHolder();

			} else if (this.type.equals("order")) {

				layout = R.layout.activity_main;
				holder = new orderHolder();

			} else if (this.type.equals("cart")) {

				layout = R.layout.row_cart;
				holder = new cartHolder();

			} else if (this.type.equals("address")) {

				layout = R.layout.row_add;
				holder = new orderHolder();

			} else if (this.type.equals("orderItem")) {

				layout = R.layout.activity_main;
				holder = new radioHolder();

			}

			convertView = vi.inflate(layout, null);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Item navitem = currentList.get(position);
		// holder.picture =navitem.getImg();
		// holder.picture ;
		// holder.name = (TextView) convertView.findViewById(R.id.name);
		// holder.name.setText(navitem.getTitle());

		// holder.picture = (ImageView) convertView.findViewById(R.id.picture);
		// holder.picture.setImageResource(navitem.getImg());

		// holder.name.setTag(navitem);

		return convertView;
	}
}