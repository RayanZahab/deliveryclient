package info.androidhive.tabsswipe;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	public void addUser(View view) {
		
		EditText username = (EditText) findViewById(R.id.name);
		EditText inputphone = (EditText) findViewById(R.id.phone);
		EditText pass = (EditText) findViewById(R.id.password);
		EditText pass2 = (EditText) findViewById(R.id.password2);

		String serverURL = new myURL(null, "customers", "register", 0).getURL();
		String method = "Upload";
		User user = new User();
		user.setName(username.getText().toString());
		user.setPhone(inputphone.getText().toString());
		if(pass.getText().toString().equals(pass2.getText().toString()))
		{
			user.setEncPassword(pass.getText().toString());
			ValidationError valid = user.validate(false);
			
			if (valid.isValid(this)) {
				new MyJs("login", this, ((deliveryclient) this.getApplication()),
						method, (Object) user, true, false).execute(serverURL);
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Pass dont match",
					Toast.LENGTH_SHORT).show();
		}
	}
	public void callMethod(String m, String s, String error) {
		Intent i = new Intent(this, LoginActivity.class);
		startActivity(i);
	}

}