package com.mobilife.delivery.client.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobilife.delivery.client.model.CodeVerificationRequest;
import com.mobilife.delivery.client.model.ConfirmationMode;
import com.mobilife.delivery.client.model.User;
import com.mobilife.delivery.client.utilities.RZHelper;
import com.mobilife.delivery.client.utilities.myURL;
import com.mobilife.delivery.client.R;

public class RegisterConfirmationActivity extends Activity {

	private EditText codeEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_register_code);
		codeEditText = (EditText) findViewById(R.id.name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_confirmation, menu);
		return true;
	}

	public void checkCode(View view){
		if(codeEditText.getText()!=null && !codeEditText.getText().equals("")){
			Bundle extras = getIntent().getExtras();
			if(extras!=null)
			{
				User user = (User) extras.get("registerUser");
				ConfirmationMode confirmationMode = (ConfirmationMode) extras.get("confirmationMode");
				if(confirmationMode.equals(ConfirmationMode.NewUser)){
					CodeVerificationRequest codeVerificationRequest = new CodeVerificationRequest();
					codeVerificationRequest.setMobileNumber(user.getPhone());
					codeVerificationRequest.setCode(codeEditText.getText().toString());
					_checkCodeRequest(codeVerificationRequest);
				}
			}
		}else{
			Toast.makeText(getApplicationContext(), "please Add Valid Code Request",Toast.LENGTH_SHORT).show();
		}
	}
	private void _checkCodeRequest(CodeVerificationRequest codeVerificationRequest) {	
		String serverURL = new myURL("customers/confirm_registeration", null, 0,30).getURL();
		RZHelper p = new RZHelper(serverURL, this, "finish", true);
		p.post(codeVerificationRequest);
	}

	public void callMethod(String m, String s, String error) {
		if (m.equals("finish"))
			finish(s, error);
	}

	private void finish(String s, String error) {
		if(error==null || !(error.length()>0)){
			Intent intent = new Intent(RegisterConfirmationActivity.this,LoginActivity.class);
			startActivity(intent);
		}
	}


}
