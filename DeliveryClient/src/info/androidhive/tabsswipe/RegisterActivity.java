package info.androidhive.tabsswipe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	public void addUser(View view) {
		EditText username = (EditText) findViewById(R.id.user_name);
		EditText inputphone = (EditText) findViewById(R.id.name);

		String serverURL = new myURL(null, "customers", "register", 0).getURL();
		String method = "Upload";
		User user = new User();
		user.setName(username.getText().toString());
		user.setPhone(inputphone.getText().toString());
		user.setEncPassword(inputphone.getText().toString());
		ValidationError valid = user.validate(false);
		
		if (valid.isValid(this)) {
			new MyJs("login", this, ((deliveryclient) this.getApplication()),
					method, (Object) user, true, false).execute(serverURL);

		}
	}
	public void callMethod(String m, String s, String error) {
		Intent i = new Intent(this, LoginActivity.class);
		startActivity(i);
	}

}