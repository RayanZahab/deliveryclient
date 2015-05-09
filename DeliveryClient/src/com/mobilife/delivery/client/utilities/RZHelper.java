package com.mobilife.delivery.client.utilities;


import java.io.File;
import java.lang.reflect.Method;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.mobilife.delivery.client.DeliveryClientApplication;
import com.mobilife.delivery.client.view.dialog.TransparentProgressDialog;
import com.mobilife.delivery.client.R;

public class RZHelper {
	private static AjaxCallback<JSONObject> callBack;
	private Activity currentActivity;
	private String returnMethod;
	private String url;
	private AQuery myAQuery;
	GlobalM glob = new GlobalM();
	protected TransparentProgressDialog loader;
	private boolean show = false;
	private boolean first = true, last = true;

	public RZHelper(String myurl, Activity activity, String method, boolean isShow) {
		this.url = myurl;
		this.currentActivity = activity;
		this.returnMethod = method;
		this.show = isShow;
		this.myAQuery = new AQuery(currentActivity);
		if (checkInternetConnection()) {
			if (show)
				loader = new TransparentProgressDialog(currentActivity,R.drawable.spinner);

			callBack = new AjaxCallback<JSONObject>() {
				
				@Override
				public void callback(String url, JSONObject html,AjaxStatus status) {
					String reply = "", error = null, stringType = "";
					if (html != null)
						reply = html.toString();
					if (status != null)
						error = status.getError();
					if (error != null) {
						Toast.makeText(myAQuery.getContext(),ErrorHandlerManager.getInstance().getErrorString(currentActivity, error), Toast.LENGTH_LONG).show();
					}else {
						Method returnFunction;
						try {
							returnFunction = currentActivity.getClass().getMethod("callMethod", stringType.getClass(), stringType.getClass(),stringType.getClass());
							returnFunction.invoke(currentActivity, returnMethod, reply,error);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			};
			callBack.header("Accept", "application/json");
			callBack.header("Accept-Encoding", "gzip");
			callBack.header("Cache-Control", "max-stale=0,max-age=60");
			callBack.header("Content-Type", "application/json; charset=utf-8");

			SharedPreferences settings1 = currentActivity.getSharedPreferences("PREFS_NAME", 0);
			String token = settings1.getString("token", "");
			if (!token.isEmpty()) {
				callBack.header("auth_token", token);
			}
		}
	}

	public RZHelper(String myurl, Activity activity, String method, boolean isfirst,boolean islast) {
		this.url = myurl;
		this.currentActivity = activity;
		this.returnMethod = method;
		this.first = isfirst;
		this.last = islast;
		this.show = false;
		this.myAQuery = new AQuery(currentActivity);
		if (checkInternetConnection()) {
			if (first) {
				loader = new TransparentProgressDialog(currentActivity,
						R.drawable.spinner);
				DeliveryClientApplication.loader = loader;
				DeliveryClientApplication.loader.show();
			}
			callBack = new AjaxCallback<JSONObject>() {
				@Override
				public void callback(String url, JSONObject html,AjaxStatus status) {
					String reply = "", error = null, stringType = "";
					if (html != null)
						reply = html.toString();
					if (status != null)
						error = status.getError();
					if (error != null) {
						Toast.makeText(myAQuery.getContext(),ErrorHandlerManager.getInstance().getErrorString(currentActivity, error), Toast.LENGTH_LONG).show();
					} else {
						Method returnFunction;
						try {							
							returnFunction = currentActivity.getClass().getMethod("callMethod", stringType.getClass(), stringType.getClass(),stringType.getClass());
							returnFunction.invoke(currentActivity, returnMethod, reply,error);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (last && DeliveryClientApplication.loader != null) {
						DeliveryClientApplication.loader.dismiss();
					}
				}
			};
			SharedPreferences settings1 = currentActivity.getSharedPreferences("PREFS_NAME", 0);
			String token = settings1.getString("token", "");
			if (!token.isEmpty()) {
				callBack.header("auth_token", token);
			}
		}
	}

	RZHelper(final ImageView imageView, final String myurl, Activity a) {
		url = myurl;
		currentActivity = a;
		show = false;
		myAQuery = new AQuery(currentActivity);

		if (checkInternetConnection()) {
			myAQuery.ajax(url, File.class, new AjaxCallback<File>() {
				public void callback(String url, File file, AjaxStatus status) {
					if (file != null) {
						myAQuery.progress(loader).id(imageView.getId()).image(myurl, false, false);
					}
				}
			});
		}
	}

	public void post(Object obj) {
		if (checkInternetConnection()) {
			JSONObject params = (new APIManager()).objToCreate(obj);
			if (loader != null && show) {
				myAQuery.progress(loader).post(url, params, JSONObject.class,callBack);
			}else {
				myAQuery.post(url, params, JSONObject.class, callBack);
			}
		}
	}

	public void get() {
		if (checkInternetConnection()) {
			if (loader != null && show) {
				myAQuery.progress(loader).ajax(url, JSONObject.class, callBack);
			} else {
				myAQuery.ajax(url, JSONObject.class, callBack);
			}
		}
	}

	public void put(Object obj) {
		if (checkInternetConnection()) {
			JSONObject params = (new APIManager()).objToCreate((Object) obj);
			if (loader != null && show) {
				myAQuery.progress(loader).put(url, params, JSONObject.class,callBack);
			} else {
				myAQuery.put(url, params, JSONObject.class, callBack);
			}
		}
	}

	public void delete() {
		if (checkInternetConnection()) {
			if (loader != null && show) {
				myAQuery.progress(loader).delete(url, JSONObject.class, callBack);
			} else {
				myAQuery.delete(url, JSONObject.class, callBack);
			}
		}
	}

	private boolean checkInternetConnection() {
		currentActivity.getApplicationContext();
		ConnectivityManager connectivityManager = (ConnectivityManager) currentActivity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		
		if(! (activeNetworkInfo != null && activeNetworkInfo.isConnected())){
			Toast t = Toast.makeText(myAQuery.getContext(), currentActivity.getString(R.string.no_net),	Toast.LENGTH_LONG);
			t.setGravity(Gravity.TOP, 0, 0);
			t.show();
		}
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public boolean isshow() {
		return show;
	}

	public void setshow(boolean show) {
		this.show = show;
	}
}
