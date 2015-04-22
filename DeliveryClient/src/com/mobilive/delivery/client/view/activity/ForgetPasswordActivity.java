package com.mobilive.delivery.client.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.R.id;
import com.mobilive.delivery.client.R.layout;
import com.mobilive.delivery.client.R.menu;
import com.mobilive.delivery.client.model.ForgetPasswordRequest;
import com.mobilive.delivery.client.utilities.ErrorHandlerManager;
import com.mobilive.delivery.client.utilities.PhoneInfoManager;
import com.mobilive.delivery.client.utilities.RZHelper;
import com.mobilive.delivery.client.utilities.myURL;

public class ForgetPasswordActivity extends Activity {

	private EditText mobileNumberEditText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
		mobileNumberEditText = (EditText) findViewById(R.id.name);
	}

	public void getNewPass(View view){
		if(mobileNumberEditText.getText()!=null && !mobileNumberEditText.getText().equals("")){
				ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
				forgetPasswordRequest.setMobileNumber(mobileNumberEditText.getText().toString());
				forgetPasswordRequest.setMobileImei(PhoneInfoManager.getPhoneImei(getApplicationContext()));
				_checkCodeRequest(forgetPasswordRequest);
		}else{
			Toast.makeText(getApplicationContext(), "please Add your Number",Toast.LENGTH_SHORT).show();
		}
	}
	private void _checkCodeRequest(ForgetPasswordRequest forgetPasswordRequest) {	
		String serverURL = new myURL("customers/forget_password", null, 0,30).getURL();
		RZHelper p = new RZHelper(serverURL, this, "finish", true);
		p.post(forgetPasswordRequest);
	}

	public void callMethod(String m, String s, String error) {
		if (m.equals("finish"))
			finish(s, error);
	}

	private void finish(String s, String error) {
		if(error==null || !(error.length()>0)){
			Toast.makeText(getApplicationContext(), ErrorHandlerManager.getInstance().getErrorString(this, "check your Messages to get your password"),Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this,LoginActivity.class);
			startActivity(intent);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forget_password, menu);
		return true;
	}

}
