package com.mobilive.delivery.client.utilities;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.mobilive.delivery.client.DeliveryClientApplication;
import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.model.User;
import com.mobilive.delivery.client.view.activity.MainActivity;
import com.mobilive.delivery.client.view.activity.RegisterActivity;
import com.mobilive.delivery.client.view.dialog.TransparentProgressDialog;
import com.mobilive.delivery.client.view.fragment.ProfileFragment;

// Class with extends AsyncTask class

public class MyJs extends AsyncTask<String, Void, Void> {

	private String content;
	private String returnFunction, Error;
	String data = "";
	public TextView uiUpdate;
	int sizeData = 0;
	private Activity mc;
	private String method;
	boolean first = false;
	private boolean last = true;
	private Object objectToAdd;
	String token;
	private DeliveryClientApplication global;
	TransparentProgressDialog pd;

	private void MyJs() {
		try {
			token = global.getToken();
		} catch (Exception exp) {
			Log.d("exep", "excep" + exp.toString());
		}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) mc
				.getApplicationContext().getSystemService(
						mc.getApplicationContext().CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public MyJs(String returnFunction, Activity m, DeliveryClientApplication mg,
			String method) {
		this.returnFunction = returnFunction;
		this.mc = m;
		this.global = mg;
		this.method = method;
		this.last = true;
		this.first = true;
		MyJs();
	}

	public MyJs(String returnFunction, Activity m, DeliveryClientApplication mg,
			String method, boolean sm, boolean last) {
		this.returnFunction = returnFunction;
		this.mc = m;
		this.global = mg;
		this.method = method;
		this.first = sm;
		this.setLast(last);
		MyJs();
	}

	public MyJs(String returnFunction, Activity m, DeliveryClientApplication mg,
			String method, Object o, boolean first, boolean last) {
		this.returnFunction = returnFunction;
		this.mc = m;
		this.global = mg;
		this.method = method;
		this.objectToAdd = o;
		this.setfirst(first);
		this.setLast(last);
		MyJs();
	}
	public MyJs(String returnFunction, Activity m, DeliveryClientApplication mg,
			String method, Object o) {
		this.returnFunction = returnFunction;
		this.mc = m;
		this.global = mg;
		this.method = method;
		this.objectToAdd = o;
		this.last = true;
		this.first = true;
		MyJs();
	}

	protected void onPreExecute() {
		if (!isNetworkAvailable()) {
			cancel(true);
			//new GlobalM().bkToNav(mc, mc.getString(R.string.no_net));
		} else {
			if (this.first) {
				showProg();
			}
		}
	}

	// Call after onPreExecute returnFunction
	protected Void doInBackground(String... urls) {

		/************ Make Post Call To Web Server ***********/
		BufferedReader reader = null;

		try {

			URL url = new URL(urls[0]);
			Log.d("ray","URL: "+url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.addRequestProperty("auth_token", token);

			if (this.method.equals("Upload")) {
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Accept-Charset", "UTF-8");
				conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
			} else {
				conn.setRequestMethod(this.method);
				conn.setRequestProperty("Accept-Charset", "UTF-8");
				conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
			}

			if (this.method.equals("GET")) {
				if (conn.getResponseCode() != 200) {
					Error = getConnError(conn);
				} else {
					Error = null;
					reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					content = sb.toString();					
				}

			} else if (this.method.equals("POST")) {
				conn.addRequestProperty("Accept", "application/json");
				conn.addRequestProperty("Accept-Encoding", "gzip");
				conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
				conn.addRequestProperty("Cache-Control","max-stale=0,max-age=60");
				conn.setRequestProperty("Accept-Charset", "UTF-8");
				conn.setDoOutput(true);
				conn.setDoInput(true);

				JSONObject jsonObjSend = (new APIManager()).objToCreate(this.objectToAdd);
				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(jsonObjSend.toString());
				wr.flush();
				wr.close();

				if (conn.getResponseCode() != 201&& conn.getResponseCode() != 200) {
					content = conn.getResponseMessage();
					Error = getConnError(conn);
				} else {
					BufferedReader responseContent = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = responseContent.readLine()) != null) {
						sb.append(line + "\n");
					}
					content = sb.toString();
					Error = null;
				}
			} else if (this.method.equals("PUT")) {
				conn.addRequestProperty("Accept", "application/json");
				conn.addRequestProperty("Accept-Encoding", "gzip");
				conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
				conn.addRequestProperty("Cache-Control","max-stale=0,max-age=60");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				JSONObject jsonObjSend = (new APIManager())
						.objToCreate(this.objectToAdd);
				OutputStreamWriter wr = new OutputStreamWriter(
						conn.getOutputStream());
				Log.d("ray","sending : "+jsonObjSend.toString());
				wr.write(jsonObjSend.toString());
				wr.flush();
				wr.close();

				if (conn.getResponseCode() != 200) {
					content = null;
					Error = getConnError(conn);
				} else {
					content = "done";
					Error = null;
				}

			} else if (this.method.equals("DELETE")) {
				conn.addRequestProperty("Accept", "application/json");
				conn.addRequestProperty("Accept-Encoding", "gzip");
				conn.addRequestProperty("Cache-Control","max-stale=0,max-age=60");
				if (conn.getResponseCode() != 204) {
					content = null;
					Error = getConnError(conn);
				} else {
					content = "done";
					Error = null;
				}
			} else if (this.method.equals("Upload")) {
				User p = (User) this.objectToAdd;
				content = register(p, url, token);
				System.out.print(content);
			}
		} catch (Exception ex) {
			Error = ex.getLocalizedMessage();
			ex.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception ex) {
			}
		}
		/*****************************************************/
		return null;
	}

	private String getConnError(HttpURLConnection conn) {
		try {
			Error = conn.getResponseMessage();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getErrorStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			return response.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected void onPostExecute(Void unused) {
		if (DeliveryClientApplication.loader != null && last) {
			DeliveryClientApplication.loader.dismiss();
		}
		try {
			if (content == null)
				content = "";
			if (Error != null) {
				if (DeliveryClientApplication.loader != null && last)
					DeliveryClientApplication.loader.dismiss();
				//new GlobalM().bkToNav(mc, getError(content, Error));
				if(mc instanceof MainActivity){
					if(((MainActivity) mc).getActiveFragment() instanceof ProfileFragment){
						Method returnFunction = this.mc.getClass().getMethod("callMethod", content.getClass(), content.getClass(),content.getClass());
						returnFunction.invoke(this.mc, this.returnFunction, content,Error);
					}
				}else if(mc instanceof RegisterActivity){
					Method returnFunction = this.mc.getClass().getMethod("callMethod", content.getClass(), content.getClass(),content.getClass());
					returnFunction.invoke(this.mc, this.returnFunction, content,Error);
				}
			}else{
				Method returnFunction = this.mc.getClass().getMethod("callMethod", content.getClass(), content.getClass(),content.getClass());
				returnFunction.invoke(this.mc, this.returnFunction, content,Error);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean getfirst() {
		return first;
	}

	public void setfirst(boolean first) {
		this.first = first;
	}

	public DeliveryClientApplication getGlobal() {
		return global;
	}

	public void setGlobal(DeliveryClientApplication global) {
		this.global = global;
	}

	@SuppressWarnings("rawtypes")
	private String register(User p, URL url, String token) throws Exception {

		String USER_AGENT = "Mozilla/5.0";
		String boundary = "*****";
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		URL obj = url;
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.addRequestProperty("Content-Type", "application/json; charset=utf-8");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Accept-Charset", "UTF-8");
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="+ boundary);

		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		DataOutputStream dos = new DataOutputStream(con.getOutputStream());

		Map<String, String> paramsVal = new HashMap<String, String>();
		paramsVal.put("name", "" + p.getName());
		paramsVal.put("phone", "" + p.getPhone());
		paramsVal.put("mobile", "" + p.getPhone());
		paramsVal.put("encrypted_password", "" + p.getPassword());
		paramsVal.put("gender", p.getGender().toString());
        paramsVal.put("device_id", p.getImei());
		for (String key : paramsVal.keySet()) {
			String value = paramsVal.get(key);
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\""+ key + "\"" + lineEnd);
			dos.writeBytes(lineEnd);
			if(key.equals("encrypted_password"))
				dos.writeBytes(value.trim());
			else
				dos.writeUTF(value.trim());
			dos.writeBytes(lineEnd);
		}
		
		dos.writeBytes("\r\n--" + boundary + "--\r\n");
		dos.flush();

		dos.close();

		int responseCode = con.getResponseCode();
		if (responseCode != 201 && responseCode != 200) {
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			Error = getConnError(con);
			System.out.println("Response Code : " + Error);

			return null;
		} else {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			return responseCode+"";
		}

	}

	public void showProg() {
		Handler h;
		Runnable r;
		h = new Handler();
		DeliveryClientApplication.loader = new TransparentProgressDialog(mc, R.drawable.spinner);
		r = new Runnable() {
			@Override
			public void run() {
				if (DeliveryClientApplication.loader.isShowing()) {
					DeliveryClientApplication.loader.dismiss();
				}
			}
		};
		if (!DeliveryClientApplication.loader.isShowing()) 
			DeliveryClientApplication.loader.show();
		h.postDelayed(r, 100000);
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	

	public String getError(String cont, String Error) {
		JSONObject jsonResponse;
		try {
			jsonResponse = new JSONObject(Error);
			if (jsonResponse.has("error"))
				return jsonResponse.optString("error").toString();
			else
				return cont;
		} catch (JSONException e) {
			return cont;
		}
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
