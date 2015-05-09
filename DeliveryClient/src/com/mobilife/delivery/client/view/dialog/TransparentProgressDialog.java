package com.mobilife.delivery.client.view.dialog;

import com.mobilife.delivery.client.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TransparentProgressDialog extends Dialog {

	private ImageView iv;
	Activity currentActivity;

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
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int w = dm.widthPixels;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w / 2,w / 2);
		iv = new ImageView(context);
		iv.setImageResource(resourceIdOfImage);
		layout.addView(iv, params);
		addContentView(layout, params);
	}

	@Override
	public void show() {
		super.show();
		RotateAnimation anim = new RotateAnimation(360.0f, 0.0f,Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,.5f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(2000);
		iv.setAnimation(anim);
		iv.startAnimation(anim);
	}
}
