package com.algosys.devi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends Activity implements AnimationListener {
	Animation slideUp;
	ImageView curtain;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	setContentView(R.layout.splash);
	slideUp = AnimationUtils.loadAnimation(getApplicationContext(),
			R.anim.slideup);
	slideUp.setAnimationListener(this);
	curtain = (ImageView)findViewById(R.id.curtain_layout);
	
	curtain.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			curtain.startAnimation(slideUp);
		}
	});
	    // TODO Auto-generated method stub
	}
	@Override
	public void onAnimationEnd(Animation arg0) {
		// TODO Auto-generated method stub
		Intent i = new Intent(Splash.this,DashBoard.class);
		startActivity(i);
	}
	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

}
