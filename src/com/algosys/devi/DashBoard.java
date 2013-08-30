package com.algosys.devi;

import com.google.android.maps.MapView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DashBoard extends Activity implements AnimationListener,
		OnClickListener {

	ImageView photos, onlinePooja, makeWish, details, video, liveDarshan;
	Animation animFadein, rightLeft,leftRight,slideup;
	static int activityId = -1;
	ImageView curt1,curt2;
	//ImageView backLayout;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		photos = (ImageView) findViewById(R.id.image_photos);
		onlinePooja = (ImageView) findViewById(R.id.image_onlinePooja);
		makeWish = (ImageView) findViewById(R.id.image_mekeWish);
		details = (ImageView) findViewById(R.id.image_details);
		video = (ImageView) findViewById(R.id.image_video);
		liveDarshan = (ImageView) findViewById(R.id.image_liveDarshan);
		curt1 = (ImageView)findViewById(R.id.curtain_layout1);
		
		//backLayout = (ImageView)findViewById(R.id.backlayout);
		//backLayout.setAlpha(80);
		//curt2 = (ImageView)findViewById(R.id.curtain_layout2);

		photos.setOnClickListener(this);
		onlinePooja.setOnClickListener(this);
		makeWish.setOnClickListener(this);
		details.setOnClickListener(this);
		video.setOnClickListener(this);
		liveDarshan.setOnClickListener(this);

		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fadein);
		rightLeft = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.right_left);
		leftRight = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.left_right);
		slideup = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slideup);
		animFadein.setAnimationListener(this);
		rightLeft.setAnimationListener(this);
		leftRight.setAnimationListener(this);
		curt1.startAnimation(slideup);
		
		//curt2.startAnimation(leftRight);
		// TODO Auto-generated method stub
		/*curt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("log_info", "curtain clicked");
				curt.startAnimation(animFadeout);
			}
		});*/

	}

	@Override
	public void onAnimationEnd(Animation arg0) {

		// TODO Auto-generated method stub
		Log.i("log_info", "annimation type is" + arg0);
		/*Intent i;
		switch (activityId) {
	
		case 1:
			i = new Intent(DashBoard.this, Wallpapers.class);
			startActivity(i);
			break;
		case 2:
			i = new Intent(DashBoard.this, AudioVideo.class);
			startActivity(i);
			break;
		case 3:
			i = new Intent(DashBoard.this, PlaceWish.class);
			startActivity(i);
			break;
		case 4:
			i = new Intent(DashBoard.this, OnlinePooja.class);
			startActivity(i);
			break;
		case 5:
			i = new Intent(DashBoard.this, Details.class);
			startActivity(i);
			break;
		case 6:
			i = new Intent(DashBoard.this, LiveDarshan.class);
			startActivity(i);
			break;
		}
*/
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub\
		Log.i("log_info", "animation start");

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		  Intent i;
		switch (v.getId()) {
       
		case R.id.image_photos:
			photos.startAnimation(animFadein);
			i = new Intent(DashBoard.this, Wallpapers.class);
			startActivity(i);
			activityId = 1;

			break;
		case R.id.image_video:
			video.startAnimation(animFadein);
			i = new Intent(DashBoard.this, AudioVideo.class);
			startActivity(i);
			activityId = 2;
			break;
		case R.id.image_mekeWish:
			makeWish.startAnimation(animFadein);
			i = new Intent(DashBoard.this, PlaceWish.class);
			startActivity(i);
			activityId = 3;
			break;
		case R.id.image_onlinePooja:
			onlinePooja.startAnimation(animFadein);
			i = new Intent(DashBoard.this, OnlinePooja.class);
			startActivity(i);
			activityId = 4;
			break;
		case R.id.image_details:
			details.startAnimation(animFadein);
			i = new Intent(DashBoard.this, Details.class);
			startActivity(i);
			activityId = 5;
			break;
		case R.id.image_liveDarshan:
			liveDarshan.startAnimation(animFadein);
			 
			
			i = new Intent(DashBoard.this, LiveDarshan.class);
			startActivity(i);
			activityId = 6;
			break;
		

		}
	}

}
