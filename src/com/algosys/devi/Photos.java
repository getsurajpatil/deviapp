package com.algosys.devi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Photos extends Activity {
ImageView wallpaper,latestPhotos,templePhotos,mataPhotos,savedPhotos;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	setContentView(R.layout.photos);
	wallpaper = (ImageView)findViewById(R.id.image_wallpapers);
	latestPhotos = (ImageView)findViewById(R.id.image_latest);
	templePhotos = (ImageView)findViewById(R.id.image_temple);
	mataPhotos = (ImageView)findViewById(R.id.image_all_forms);
	savedPhotos = (ImageView)findViewById(R.id.image_saved);
	
	
	wallpaper.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i  = new Intent(Photos.this,Wallpapers.class);
			i.putExtra("messageid", "wallpapers");
			startActivity(i);
		}
	});
	
	latestPhotos.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i  = new Intent(Photos.this,Wallpapers.class);
			i.putExtra("messageid", "latestPhotos");
			startActivity(i);
		}
	});
	templePhotos.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i  = new Intent(Photos.this,Wallpapers.class);
		i.putExtra("messageid", "templePhotos");
		startActivity(i);
	}
    });
	mataPhotos.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i  = new Intent(Photos.this,Wallpapers.class);
		i.putExtra("messageid", "mataPhotos");
		startActivity(i);
	}
    });
	savedPhotos.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i  = new Intent(Photos.this,Wallpapers.class);
		i.putExtra("messageid", "savedPhotos");
		startActivity(i);
	}
    });
	    // TODO Auto-generated method stub
	}

}
