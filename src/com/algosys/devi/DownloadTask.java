package com.algosys.devi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

/**
 * This is not a real download task.
 * It just sleeps for some random time when it's launched. 
 * The idea is not to require a connection and not to eat it.
 * 
 */
public class DownloadTask implements Runnable {

	private static final String TAG = DownloadTask.class.getSimpleName();
	
	private static final Random random = new Random();
	
	private int lengthSec;
	String imageuri;
	Bitmap bitmap;
	Bitmap bmp;
	URL url;
	Wallpapers wall = new Wallpapers();
	public DownloadTask(String url) {
	//lengthSec = random.nextInt(3) + 1;
		imageuri = url;

	}
	public DownloadTask() {
		//lengthSec = random.nextInt(3) + 1;
			//imageuri = url;

		}
	
	@Override
	public void run() {
		try {
			//Thread.sleep(lengthSec * 1000);
			Log.i("log_info", "image url is " +imageuri);
			bitmap = getImage(imageuri);
			//wall.btmArray.add(bitmap);
			// it's a good idea to always catch Throwable
			// in isolated "codelets" like Runnable or Thread
			// otherwise the exception might be sunk by some
			// agent that actually runs your Runnable - you
			// never know what it might be.
		} catch (Throwable t) {
			Log.e(TAG, "Error in DownloadTask", t);
		}
	}
	
	public Bitmap getImage(String image_url) {
		
		try {
			  url = new URL(image_url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
		 bmp = BitmapFactory.decodeStream(url.openConnection()
					.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bmp;
	}

}
