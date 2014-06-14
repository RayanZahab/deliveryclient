package info.androidhive.tabsswipe;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

public class ValidationError {
	private boolean valid = false;
	private String errorMsg = "";
	private int errorMsgId=0;

	public ValidationError(boolean is, String msg) {
		setError(is);
		setErrorMsg(msg);
	}

	public ValidationError(boolean is, int msgId) {
		setError(is);
		setErrorMsgId(msgId);
	}

	public boolean isValid(Activity mc) {
		if(!valid)
			showError(mc);
		return valid;
	}

	public void setError(boolean valid) {
		this.valid = valid;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	private void showError(Activity mc) {
		if(errorMsgId!=0)
		{
			errorMsg = mc.getString(errorMsgId);
		}
		Toast ts = Toast.makeText(mc, errorMsg, Toast.LENGTH_SHORT);
		ts.setGravity(Gravity.TOP, 0, 0);
		ts.show();
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getErrorMsgId() {
		return errorMsgId;
	}

	public void setErrorMsgId(int errorMsgId) {
		this.errorMsgId = errorMsgId;
	}

}
