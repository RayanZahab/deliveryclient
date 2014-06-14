package info.androidhive.tabsswipe;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
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
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

// Class with extends AsyncTask class

public class MyJs extends AsyncTask<String, Void, Void> {

	private String Content;
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
	private deliveryclient global;
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

	public MyJs(String returnFunction, Activity m, deliveryclient mg, String method) {
		this.returnFunction = returnFunction;
		this.mc = m;
		this.global = mg;
		this.method = method;
		this.last = true;
		this.first = true;
		MyJs();
	}

	public MyJs(String returnFunction, Activity m, deliveryclient mg,
			String method, boolean sm, boolean last) {
		this.returnFunction = returnFunction;
		this.mc = m;
		this.global = mg;
		this.method = method;
		this.first = sm;
		this.setLast(last);
		MyJs();
	}

	public MyJs(String returnFunction, Activity m, deliveryclient mg,
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

	public MyJs(String returnFunction, Activity m, deliveryclient mg,
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
			new GlobalM().bkToNav(mc, mc.getString(R.string.no_net));
		} else {
			if (this.first) {
				Log.d("rays","method: "+returnFunction+"->"+last);
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

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.addRequestProperty("auth_token", token);

			if (this.method.equals("Upload")) {
				conn.setRequestMethod("POST");
			} else {
				conn.setRequestMethod(this.method);
				conn.setRequestProperty("Content-Type",
						"application/json; charset=utf-8");
			}

			if (this.method.equals("GET")) {
				reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				Content = sb.toString();
				if (conn.getResponseCode() != 200) {
					Error = conn.getResponseMessage();
					JSONObject jsonResponse = new JSONObject(Content);
					Error = jsonResponse.optString("error").toString();
				} else {
					Error = null;
				}
			} else if (this.method.equals("POST")) {
				conn.addRequestProperty("Accept", "application/json");
				conn.addRequestProperty("Accept-Encoding", "gzip");
				conn.addRequestProperty("Cache-Control",
						"max-stale=0,max-age=60");
				conn.setDoOutput(true);
				conn.setDoInput(true);

				JSONObject jsonObjSend = (new APIManager())
						.objToCreate(this.objectToAdd);
				OutputStreamWriter wr = new OutputStreamWriter(
						conn.getOutputStream());
				Log.d("ray", "ray writing: "+urls[0]+"->" + jsonObjSend.toString());
				wr.write(jsonObjSend.toString());
				wr.flush();
				wr.close();

				if (conn.getResponseCode() != 201
						&& conn.getResponseCode() != 200) {
					Log.d("ray",
							"Failed: " + url + "\n" + conn.getResponseMessage());
					Content = conn.getResponseMessage();
					Error = conn.getResponseMessage();
				} else {
					BufferedReader responseContent = new BufferedReader(
							new InputStreamReader(conn.getInputStream()));
					StringBuilder sb = new StringBuilder();
					String line = null;

					while ((line = responseContent.readLine()) != null) {
						sb.append(line + "\n");
					}
					Content = sb.toString();
					Error = null;
				}
			} else if (this.method.equals("PUT")) {
				conn.addRequestProperty("Accept", "application/json");
				conn.addRequestProperty("Accept-Encoding", "gzip");
				conn.addRequestProperty("Cache-Control",
						"max-stale=0,max-age=60");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				Log.d("ray", "ray req: "
						+ conn.getRequestProperties().toString());
				JSONObject jsonObjSend = (new APIManager())
						.objToCreate(this.objectToAdd);
				OutputStreamWriter wr = new OutputStreamWriter(
						conn.getOutputStream());
				Log.d("ray", "ray writing: " +urls[0]+"->" +  jsonObjSend.toString());
				wr.write(jsonObjSend.toString());
				wr.flush();
				wr.close();

				if (conn.getResponseCode() != 200) {
					Log.d("ray",
							"Failed: " + url + "\n" + conn.getResponseMessage());
					Content = null;
					Error = conn.getResponseMessage();
				} else {
					Content = "done";
					Error = null;
				}

			} else if (this.method.equals("DELETE")) {
				conn.addRequestProperty("Accept", "application/json");
				conn.addRequestProperty("Accept-Encoding", "gzip");
				conn.addRequestProperty("Cache-Control",
						"max-stale=0,max-age=60");
				if (conn.getResponseCode() != 204) {
					Log.d("ray",
							"Failed: " + url + "\n" + conn.getResponseMessage());
					Content = null;
					Error = conn.getResponseMessage();
				} else {
					Content = "done";
					Error = null;
				}
			} else if (this.method.equals("Upload")) {
				Product p = (Product) this.objectToAdd;
				Content = uploadProduct(p,url,token);
			}
		} catch (Exception ex) {
			Error = ex.getLocalizedMessage();
			ex.printStackTrace();
			Log.d("ray", "ray err:" + Error);
		} finally {
			try {
				reader.close();
			} catch (Exception ex) {
			}
		}
		/*****************************************************/
		return null;
	}

	protected void onPostExecute(Void unused) {
		Log.d("raya","post: "+returnFunction+": "+last+ ": "+global.loader.isShowing());
		if (global.loader != null && last) {
			global.loader.dismiss();
			Log.d("raya","dismissing: ");
		}
		try {
			if (Content == null)
				Content = "";
			if (Error != null) {
				//new GlobalM().bkToNav(mc, getError(Content,Error));
			}
			Method returnFunction = this.mc.getClass()
					.getMethod(this.returnFunction, Content.getClass(),
							Content.getClass());
			returnFunction.invoke(this.mc, Content, Error);

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

	public deliveryclient getGlobal() {
		return global;
	}

	public void setGlobal(deliveryclient global) {
		this.global = global;
	}
	@SuppressWarnings("rawtypes")
	private String uploadProduct(Product p,URL url,String token) throws Exception {

		String USER_AGENT = "Mozilla/5.0";
		String boundary = "*****";
		String iFileName = "";
		String fileUrl = "";
		if (p.getPhoto() != null) {
			iFileName = p.getPhoto().getName();
			fileUrl = p.getPhoto().getUrl();
		}
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		URL obj = url;
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("auth_token", token);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="
				+ boundary);

		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		DataOutputStream dos = new DataOutputStream(con.getOutputStream());

		Map<String, String> paramsVal = new HashMap<String, String>();
		paramsVal.put("name", "" + p.getName());
		paramsVal.put("category_id", "" + p.getCategory().getId());
		paramsVal.put("shop_id", "" + p.getShop_id());
		paramsVal.put("price", "" + p.getPrice());
		paramsVal.put("unit_id", "" + p.getUnit().getId());
		paramsVal.put("photo_name", "" + iFileName);
		paramsVal.put("description", "" + p.getDescription());
		Iterator iterator = paramsVal.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\""
					+ mapEntry.getKey() + "\"" + lineEnd);
			System.out.println("\n Content-Disposition: form-data; name=\""
					+ mapEntry.getKey() + "\"" + lineEnd);
			dos.writeBytes(lineEnd);
			dos.writeBytes(mapEntry.getValue().toString());
			System.out.println("\n :" + mapEntry.getValue().toString());
			dos.writeBytes(lineEnd);
		}

		if (p.getPhoto() != null) {
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"photo\";filename=\""
					+ iFileName + "\"" + lineEnd);
			dos.writeBytes(lineEnd);
			FileInputStream fileInputStream = new FileInputStream(fileUrl);
			int bytesAvailable = fileInputStream.available();

			int maxBufferSize = 1024;
			int bufferSize = Math.min(bytesAvailable, maxBufferSize);
			byte[] buffer = new byte[bufferSize];

			// read file and write it into form...
			int bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			System.out.println("\nRay:" + buffer);

			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}
			dos.writeBytes(lineEnd);
			dos.writeBytes("\r\n--" + boundary + "--\r\n");
			// close streams
			fileInputStream.close();
		}
		else
			dos.writeBytes("\r\n--" + boundary + "--\r\n");
		dos.flush();

		dos.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode + "->"
				+ con.getResponseMessage());

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		return response.toString();

	}

	public void showProg() {
		Handler h;
		Runnable r;
		h = new Handler();
		global.loader = new TransparentProgressDialog(mc, R.drawable.spinner);
		r = new Runnable() {
			@Override
			public void run() {
				if (global.loader.isShowing()) {
					global.loader.dismiss();
				}
			}
		};
		global.loader.show();
		h.postDelayed(r, 10000);
	}	

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
		if(last && global.loader!=null)
			global.loader.dismiss();
	}

	public class TransparentProgressDialog extends Dialog {

		private ImageView iv;

		public TransparentProgressDialog(Context context, int resourceIdOfImage) {
			super(context, R.style.TransparentProgressDialog);
			WindowManager.LayoutParams wlmp = getWindow().getAttributes();
			wlmp.gravity = Gravity.CENTER_HORIZONTAL;
			getWindow().setAttributes(wlmp);
			setTitle(null);
			setCancelable(false);
			setOnCancelListener(null);
			LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.VERTICAL);
			DisplayMetrics dm = new DisplayMetrics();
			mc.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int w = dm.widthPixels;
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					w/2, w/2);
			iv = new ImageView(context);
			iv.setImageResource(resourceIdOfImage);
			layout.addView(iv, params);
			addContentView(layout, params);
		}

		@Override
		public void show() {
			super.show();
			
			RotateAnimation anim = new RotateAnimation(360.0f, 0.0f,
					Animation.RELATIVE_TO_SELF, .5f,
					Animation.RELATIVE_TO_SELF, .5f);
			anim.setInterpolator(new LinearInterpolator());
			anim.setRepeatCount(Animation.INFINITE);
			anim.setDuration(2000);
			iv.setAnimation(anim);
			iv.startAnimation(anim);
		}
	}

	public String getError(String cont,String Error) {
		Log.d("rays","error: "+cont+","+Error+"->"+returnFunction);
		JSONObject jsonResponse;
		try {
			jsonResponse = new JSONObject(cont);
			if(jsonResponse.has("error"))
				return jsonResponse.optString("error").toString();
			else
				return cont;
		} catch (JSONException e) {
			return cont;
		}
	}

}
