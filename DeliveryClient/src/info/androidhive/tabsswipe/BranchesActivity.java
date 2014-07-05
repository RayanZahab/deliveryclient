package info.androidhive.tabsswipe;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class BranchesActivity extends Activity {

	public MyCustomAdapter dataAdapter = null;
	ArrayList<Branch> branches;
	ArrayList<Item> branchesItem;
	int shopId;
	Cart cart;
	ArrayList<Item> mylist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_branches);

		cart = ((deliveryclient) this.getApplication()).getMyCart();
		getProducts();
	}

	public void getProducts() {
		mylist = new ArrayList<Item>();
		for (CartItem myP : cart.getCartItems()) {
			Product myProduct = myP.getProduct();
			Item it = new Item();
			it.setName(myProduct.toString());
			it.setType("preview");
			it.setPrice(myProduct.getPrice());
			it.setId(myProduct.getId());
			mylist.add(it);
		}
		updateList();
	}

	public void updateList() {
		ListView listView = (ListView) findViewById(R.id.list);
	
		dataAdapter = new MyCustomAdapter(this, R.layout.row_preview,
				branchesItem);
		listView.setAdapter(dataAdapter);
	}

	public MyCustomAdapter getAdapter() {
		return dataAdapter;
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// getMenuInflater().inflate(R.menu.cat_context_menu, menu);
	}

	public boolean onContextItemSelected(MenuItem item) {
		return true;
	}

	public void Delete(final int branchId) {

		new AlertDialog.Builder(this)
				.setTitle(R.string.deletethisbranch)
				.setIcon(R.drawable.branches)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int whichButton) {
								String serverURL = new myURL(null, "branches",
										branchId, 0).getURL();
								MyJs mjs = new MyJs("afterDelete",
										BranchesActivity.this,
										((deliveryclient) BranchesActivity.this
												.getApplication()), "DELETE");
								mjs.execute(serverURL);
							}
						}).setNegativeButton(android.R.string.no, null).show();
	}

	public void afterDelete(String s, String error) {
		backToActivity(BranchesActivity.class);
	}

	public void Edit(Item item) {
		/*
		 * Intent i = new Intent(BranchesActivity.this,
		 * AddBranchActivity.class); ((deliveryclient)
		 * BranchesActivity.this.getApplication()).setBranchId(item .getId());
		 * startActivity(i);
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.branches, menu);
		// SharedMenu.onCreateOptionsMenu(this, menu, getApplicationContext(),
		// dataAdapter);
		return true;

	}

	@Override
	public void onBackPressed() {/*
								 * Intent i = new Intent(BranchesActivity.this,
								 * NavigationActivity.class); startActivity(i);
								 */
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		 * if (SharedMenu.onOptionsItemSelected(item, this) == false) { //
		 * handle local menu items here or leave blank switch (item.getItemId())
		 * { case R.id.action_search: break; case R.id.add: Intent intent = new
		 * Intent(this, AddBranchActivity.class); startActivity(intent); break;
		 * } }
		 */
		return super.onOptionsItemSelected(item);
	}

	public void backToActivity(Class activity) {
		Intent i = new Intent(BranchesActivity.this, activity);
		startActivity(i);
	}

}
