package info.androidhive.tabsswipe;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageTask extends AsyncTask<String, Void, Bitmap> {
	ImageView bmImage;
	Activity current;
	Context ctx;
	public boolean isCat = false;
	boolean exist = false;

	public ImageTask(ImageView bmImage, Context ctx) {
		this.bmImage = bmImage;
		this.ctx = ctx;
	}

	public ImageTask(ImageView bmImage, Activity current) {
		this.bmImage = bmImage;
		this.current = current;
	}

	protected void onPostExecute(Bitmap result) {
		if (exist)
			bmImage.setImageBitmap(result);
	}

	@Override
	protected Bitmap doInBackground(String... url) {
		String urldisplay = url[0];
		Bitmap mIcon11 = null;
		try {
			if (exists(urldisplay)) {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
				exist = true;
			} else {
				if (!isCat) {
					File imgFile = new File(urldisplay);
					mIcon11 = BitmapFactory.decodeFile(imgFile
							.getAbsolutePath());
					exist = true;
				}
			}
		} catch (Exception e) {

			Log.d("Error", "Error:" + e.getMessage());

		}
		return mIcon11;
	}

	public static boolean exists(String URLName) {
		try {
			try{
				URL u = new URL(URLName); // this would check for the protocol
				u.toURI();
			}catch (Exception e) {
				Log.d("Error", "Error URL:"+URLName+ " , " + e.getMessage());
				e.printStackTrace();
				return false;
			}
			HttpURLConnection.setFollowRedirects(false);
			// note : you may also need
			// HttpURLConnection.setInstanceFollowRedirects(false)
			HttpURLConnection con = (HttpURLConnection) new URL(URLName)
					.openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
