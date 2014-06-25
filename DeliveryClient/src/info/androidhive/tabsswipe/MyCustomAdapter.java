package info.androidhive.tabsswipe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

class MyCustomAdapter extends ArrayAdapter<Item> {

	private ArrayList<Item> currentList;
	Context context;
	private String type;
	List<Integer> cartIds = new ArrayList<Integer>();

	public MyCustomAdapter(Context context, int textViewResourceId,
			ArrayList<Item> navList) {
		super(context, textViewResourceId, navList);
		this.currentList = new ArrayList<Item>();
		this.currentList.addAll(navList);
		this.context = context;
		Activity activity = (Activity) context;
		cartIds = ((deliveryclient) activity.getApplication()).getMyCartIds();
		this.setType();
	}

	private void setType() {
		if(this.currentList.size()>0)
			this.type = this.currentList.get(0).getType();
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
		Button quantity;
		public productHolder(View convertView,final Item item) {
			name = (TextView) convertView.findViewById(R.id.name);
			name.setText(item.getName());
			price = (TextView) convertView.findViewById(R.id.price);
			price.setText(""+item.getPrice());
			quantity = (Button) convertView.findViewById(R.id.quantity);
			qtTxt = (TextView) convertView.findViewById(R.id.qtTxt);
			//int p = cartIds.get(item.getId());
			//if(p>0)
			//{
				
			//}
			quantity.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					int qt = Integer.parseInt(qtTxt.getText().toString());
					qt++;
					qtTxt.setText(""+qt);
					addToCart(item);
					
				}
			});
		}
	}
	public void addToCart(Item item){
		Activity activity = (Activity) context;
		Product p = new Product(item.getId());
		p.setPrice(item.getPrice());
		((deliveryclient) activity.getApplication()).addToCart(p);
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
		public txtHolder(View convertView,Item item) {
			name = (TextView) convertView.findViewById(R.id.name);
			name.setText(item.getName());
		}
	}

	class txImgHolder extends ViewHolder {
		TextView name;
		ImageView picture;
		public txImgHolder(View convertView,Item item) {
			TextView name = (TextView) convertView.findViewById(R.id.name);
			ImageView picture = (ImageView) convertView.findViewById(R.id.image);
			picture.setImageResource(item.getImg());
			name.setText(item.getName());
		}
	}

	class radioHolder extends ViewHolder {
		RadioButton name;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		Item currentItem = currentList.get(position);
		
		if (convertView == null) {
			convertView= setAttr(this.type, currentItem);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}

	public View setAttr(String type, Item currentItem) {
		ViewHolder holder = null;
		int layout = 0;
		LayoutInflater vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View convertView = null;
		
		if (this.type.equals("txt")) {
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

			layout = R.layout.row_cart;
			holder = new cartHolder();

		} else if (this.type.equals("address")) {

			layout = R.layout.row_add;
			holder = new orderHolder();

		} else if (this.type.equals("orderItem")) {

			layout = R.layout.activity_main;
			holder = new radioHolder();
		}				
		convertView.setTag(holder);

		return convertView;
	}
}