package com.algosys.devi;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Path.FillType;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Wallpapers extends Activity  {
	
	Handler handler;
	ImageView wallpaper;
	LinearLayout horLayout;
	URL url;
	URI uri;
	Bitmap bmp;
	int i;
	Context context;
	int width, height;
	Button prev, next, save,loadMore,savedImages;
	DownloadTask downloadTask;
	int posthandler = 1;
	ProgressDialog prg;
	String result;
	static int imageposition;
	Bitmap btm;
	int firstIconId,lastIconId;
	String messageid = "photos";
	//MyImageTask myimagetask = new MyImageTask();
	//Bitmap[] bitmapArray = new Bitmap[];
	static ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
	static ArrayList<File> savedImageArray = new ArrayList<File>();
	/*String[] image_url = {
			"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn1/174602_237975259559770_584761134_q.jpg",
			"http://www.era.la/wp-content/uploads/2012/12/Konark-Sun-Temple-50x50.jpg",
			"http://wscont1.apps.microsoft.com/winstore/1x/4f82c4dc-ca3b-429a-90c7-3391cde736d2/Icon.181433.png",
			"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn1/174602_237975259559770_584761134_q.jpg",
			"http://youngadventuress.com/wp-content/uploads/2013/07/20042450-shri-shantadurga-temple-kunkallikarin-fatorpa-goa-india-45x45.jpg",
			"http://s2.hubimg.com/u/8171449_50.jpg",
			"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn2/261194_114669075306525_1530311464_q.jpg",
			"http://profile.ak.fbcdn.net/hprofile-ak-ash4/373319_431184430281732_1763988630_q.jpg",
			"http://edge.ixigo.com/ixi-api/img/512ca3a7e4b0ec540bf26a54_64x64_fit.jpg"};*/
	
	static ArrayList<String> iconUrl = new ArrayList<String>();
	static HashMap<String,String> imageUrl = new HashMap<String,String>();
	//static HashMap<String,Bitmap> btmArray = new HashMap<String,Bitmap>();
	AppHolder appHolder = new AppHolder();
	MyAsyncTask syncTask = new MyAsyncTask();
	GridView grid;
	List<Integer> imgresids ,largeimgresids;
	Integer[] imageids,largeimageids;
	int dialogPosition;

	/** Called when the activity is first created. */
	@SuppressWarnings("unused")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wallpapers);
	
		context = this;
		//wallpaper = (ImageView) findViewById(R.id.wall_image);
		//horLayout = (LinearLayout) findViewById(R.id.hor_layout);
		//prev = (Button) findViewById(R.id.button_prev);
		//next = (Button) findViewById(R.id.button_next);
		//save = (Button) findViewById(R.id.button_save);
		loadMore = (Button) findViewById(R.id.button_load_more);
		savedImages = (Button) findViewById(R.id.button_saved_images);
		grid = (GridView)findViewById(R.id.grid_album);
		
		//Bundle b = getIntent().getExtras();
		//messageid = b.getString("messageid");
		//Log.i("log_info", "in wallpapers: message id is" + messageid);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Display display = getWindowManager().getDefaultDisplay();

		width = display.getWidth();
		height = display.getHeight();
		Log.i("log_info", "height width " + width + height);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				100, 100);
		LinearLayout.LayoutParams imagelayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, height / 2);
		//wallpaper.setLayoutParams(imagelayoutParams);
		
		 imgresids = new ArrayList<Integer>();
		 largeimgresids = new ArrayList<Integer>();
		 savedImages.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				messageid = "savedPhotos";
				new MySavedImageTask().execute();
				loadMore.setVisibility(View.INVISIBLE);
				savedImages.setVisibility(View.INVISIBLE);
			}
		});
		 
		/*if(messageid.equals("savedPhotos")){
				new MySavedImageTask().execute();
				loadMore.setVisibility(View.INVISIBLE);
			}else{*/
			loadMore.setVisibility(View.VISIBLE);
			savedImages.setVisibility(View.VISIBLE);
		    for(int i = 1;i<=6;i++){
			 String imageName = "photo"+i;
			 String largeimagename = "photo"+i+"_large";
             int resID = getResources().getIdentifier(imageName, "drawable", "com.algosys.devi");
             int largeresID = getResources().getIdentifier(largeimagename, "drawable", "com.algosys.devi");
             imgresids.add(resID);
             largeimgresids.add(largeresID);
		}
		imageids = new Integer[imgresids.size()];imgresids.toArray(imageids);
		largeimageids = new Integer[largeimgresids.size()];largeimgresids.toArray(largeimageids);
		ItemsAdapter itemsAdapter = new ItemsAdapter(this,
			     imageids,largeimageids,imageids.length);
		// Log.i("log_info", "set adapter called");
		grid.setAdapter(itemsAdapter);
		
			//}
		
	    loadMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				new MyAsyncTask().execute();
			}
		});
		
		//syncTask.execute();
		
	   
		}
	
	private class ItemsAdapter extends BaseAdapter {
  	  
    	private LayoutInflater mInflator;
  		int recentInteractionCount;
  		Integer[] imageresourceids;
  		Integer[] largeimageresourceids;
  		
  		
  		
    	  public ItemsAdapter(Context context, Integer[] resIds, Integer[] largeresIds, int length) {
    		  mInflator = LayoutInflater.from(context);
  			
    	   
  			imageresourceids = resIds;
  			largeimageresourceids = largeresIds;
  			
  			recentInteractionCount = length;
    	    
    	  }
    	  @Override
  		public int getCount() {
  			// TODO Auto-generated method stub
  			return recentInteractionCount;
  		}

  		@Override
  		public Object getItem(int position) {
  			// TODO Auto-generated method stub
  			return position;
  		}

  		@Override
  		public long getItemId(int position) {
  			// TODO Auto-generated method stub
  			return position;
  		}

    	  @Override
    	  public View getView(final int position, View convertView,
    	    ViewGroup parent) {
    	   
    	   if (convertView == null) {
				convertView = mInflator.inflate(R.layout.gridlayout, null);
			}
    	  
    	  ImageView imgView = (ImageView) convertView
			.findViewById(R.id.grid_image);
	       
	       
	      
	       imgView.setBackgroundResource(imageresourceids[position]);
	       
	          imgView.setOnClickListener(new OnClickListener() {
              @Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
            	  dialogPosition = position;
            	  Dialog dialog = new Dialog(Wallpapers.this);
      			 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      			//dialog.setCancelable(false);
      			 dialog.setContentView(R.layout.dialoglayout);
      			
      			
      			
      		
      			
                  final ImageView image = (ImageView)dialog.findViewById(R.id.dialog_image);
                  RelativeLayout.LayoutParams imagelayoutParams = new RelativeLayout.LayoutParams(
                  LinearLayout.LayoutParams.FILL_PARENT, height-100);
      			image.setLayoutParams(imagelayoutParams);
      			Button previous = (Button) dialog.findViewById(R.id.button_previous);
      			Button next = (Button) dialog.findViewById(R.id.button_next);
      			Button save = (Button) dialog.findViewById(R.id.button_save);
      			save.setVisibility(View.INVISIBLE);
      			 //image.setImageBitmap(btmArray.get(position));
      			 //btm = getImage("http://1.bp.blogspot.com/-fCdkZvJUUDQ/UJQqKZSwhvI/AAAAAAAAGT4/prhh8KK4emk/s400/konark-sun-temple-orissa.jpg");
      			
      			//btm = getImage(imageurl);	
      			
      			image.setImageResource(largeimageresourceids[position]);
      			next.setOnClickListener(new OnClickListener() {

      				@Override
      				public void onClick(View v) {
      					// TODO Auto-generated method stub
      					
      					int localPosition = dialogPosition+1;
      					dialogPosition = localPosition;
      					Log.i("log_info", "localPosition and dialogposition is "+localPosition+ " and " +dialogPosition);
      					if(localPosition < 6){
      						image.setImageResource(largeimageresourceids[localPosition]);
      						
      					}
      					else{
      						dialogPosition = 5;
      					Toast.makeText(context, "End of List", Toast.LENGTH_LONG).show();
      					}
      				}
      			});

      			previous.setOnClickListener(new OnClickListener() {

      				@Override
      				public void onClick(View v) {
      					// TODO Auto-generated method stub
      					
      					int localPosition = dialogPosition-1;
      					dialogPosition = localPosition;
      					Log.i("log_info", "localPosition and dialogposition is "+localPosition+ " and " +dialogPosition);
      					if(localPosition >= 0){
      						image.setImageResource(largeimageresourceids[localPosition]);
      					}else{
      						dialogPosition = 0;
      						Toast.makeText(context, "End of List", Toast.LENGTH_LONG).show();
      					}
      				}
      			});

      			save.setOnClickListener(new OnClickListener() {
      				@Override
      				public void onClick(View v) {
      					// TODO Auto-generated method stub
      					//saveImage(btm);
      				}
      			});

      			dialog.show();
            	     
            	     			
              }
			});
	          
	       //convertView.setTag(recentHolder);
    	   return convertView;
    	  }
    	}
	
	public class MyAsyncTask extends AsyncTask<Void, Void, Boolean> {

		@SuppressWarnings("static-access")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			prg = ProgressDialog.show(context, "Please Wait",
					"Loading More Images");
			// Log.i("uni_info", "Checking status updates...");
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			boolean net = hasActiveInternetConnection(context);
			if(net){
			
			/* HttpClient httpclient = new DefaultHttpClient(); 
			  HttpPost httppost = new HttpPost("http://idc.thesynapses.com/services/user_registration.php");
			  try {
				  
			  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2); 
			  nameValuePairs.add(new BasicNameValuePair("message",message));
			  nameValuePairs.add(new BasicNameValuePair("count",count));
			  nameValuePairs.add(new BasicNameValuePair("startIconid",startIconId));
			 
			 
			  
			  
			  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			  HttpResponse response = httpclient.execute(httppost);
			  
			  HttpEntity entity = response.getEntity();
			  InputStream is = entity.getContent();
			  
			  BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8); 
			  StringBuilder sb = new StringBuilder();
			  
			 String line = null; while ((line = reader.readLine()) != null) {
			 sb.append(line + "\n");
			 } //result = sb.toString();
			  
			  //result = EntityUtils.toString(entity);
			  
			  Log.i("log_info","response is "+result); } catch
			  (UnsupportedEncodingException e) {
				  // TODO Auto-generated catch  block 
				  e.printStackTrace(); Log.i("log_info","response is "+e); }
			 catch (ClientProtocolException e) { 
			  e.printStackTrace(); 
			  Log.i("log_info","response is "+e); }
			  catch (IOException e) {
			  e.printStackTrace();
			  Log.i("log_info","response is "+e); }*/
			 
			result ="{\"items\":[{\"iconId\":\"1\",\"iconUrl\":\"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn1/174602_237975259559770_584761134_q.jpg\",\"imageUrl\":\"http://www.totalprosports.com/wp-content/uploads/2013/02/god-2.jpg\"},"
					+"{\"iconId\":\"2\",\"iconUrl\":\"http://www.era.la/wp-content/uploads/2012/12/Konark-Sun-Temple-50x50.jpg\",\"imageUrl\":\"http://www.sikhnet.com/thegallery/gallery/d/88090-3/golden-temple-amritsar.jpg\"}," 
					+"{\"iconId\":\"2\",\"iconUrl\":\"http://wscont1.apps.microsoft.com/winstore/1x/4f82c4dc-ca3b-429a-90c7-3391cde736d2/Icon.181433.png\",\"imageUrl\":\"http://api.ning.com/files/SpvOjhSX9WI2whN7khjFzb50oewRfMVTdRb9wcWTwsMYhIZ2pkm*kGbZmt73dYwIDifmzVDvDsduEd4CEMRoVNVd0w8gORdO/7temple.gif\"}]}";
			
			return true;
			}else{
				
				return true;
			}
			/*Log.i("log_info", "inbackground");
			int i;
			for(i = 0;i<image_url.length;i++){
				Bitmap btm = getImage(image_url[i]);
				btmArray.add(btm);
			}
			
			return true;
		}*/
		}
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

			// Log.i("uni_info", "In onProgressUpdate..." + values[0]);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//prg.cancel();
			//prg.dismiss();
			Log.i("log_info", "In onPostExecute..." + result);
			if (result == true) {
			Log.i("log_info", "Refreshing view...");
            parseValues();
            new MyImageTask().execute();
			//grid.setAdapter(new MyAlbumGrid(context));
				
			}else{
				showDialog();
				Toast.makeText(context,"Your Internet is not working", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private  boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivityManager 
	         = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
	
	
	public  boolean hasActiveInternetConnection(Context context) {
	    if (isNetworkAvailable(context)) {
	        try {
	            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
	            urlc.setRequestProperty("User-Agent", "Test");
	            urlc.setRequestProperty("Connection", "close");
	            urlc.setConnectTimeout(1500); 
	            urlc.connect();
	            return (urlc.getResponseCode() == 200);
	        } catch (IOException e) {
	            Log.e("log_info", "Error checking internet connection", e);
	        }
	    } else {
	        Log.d("log_info", "No network available!");
	    }
	    return false;
	}
	public void showDialog(){
		AlertDialog.Builder dialog = new Builder(context);
		dialog.setMessage(
				"Do you want to enable network connection?").setIcon(
				R.drawable.alert).setTitle(
				"This application requires internet access").setCancelable(
				false).setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						Intent networkSettingsPage = new Intent();
						networkSettingsPage
								.setAction(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
						startActivity(networkSettingsPage);
					}
				}).setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						finish();
					}
				}).create().show();
	}
	public void parseValues() {
		JSONObject jObject;
		try {
			Log.i("log_info", "Parse value");
			jObject = new JSONObject(result);
			iconUrl.clear();
			imageUrl.clear();
			// result =
			// "{\"VoucherId\":\"123\",\"ValidityPeriod\":\"1 Year\",\"ParentId\":\"001\",\"PArentAddress\":\"XYZ street, Pune\",\"ContactDetails\":\"98765640123\",\"EmailID\":\"xyz@gmail.com\"}";
			// String signedArray = jObject.getString("status");
			JSONArray  items = jObject.getJSONArray("items");
			Log.i("log_info", "item length is "+items.length());
			for(int i = 0 ; i < items.length(); i++){
			    JSONObject item = items.getJSONObject(i);
			String iconid = item.getString("iconId");
			String iconurl = item.getString("iconUrl");
			String imageurl = item.getString("imageUrl");
			if(i==0){
				firstIconId = Integer.parseInt(iconid);
			}
			lastIconId = Integer.parseInt(iconid);
			Log.i("log_info","iconid is  "+iconid);
			Log.i("log_info","iconurl is  "+iconurl);
		    Log.i("log_info","imageurl is  "+imageurl);
		    
		    iconUrl.add(iconurl);
		    imageUrl.put(iconurl, imageurl);
		    
		    
			}
		     
			// Log.i("log_info","ParentAddress is  "+pAdd);
			// Log.i("log_info","ContactDetails is  "+pContact);
			// Log.i("log_info","EmailID is  "+pEmail);
			// editor.putString("KEYARRAY", signedArray);
			// editor.putString("UID", userId);
			// editor.commit();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("log_info", "inside catch  " + e);
		}

	}
	
	public class MyImageTask extends AsyncTask<Void, Void, Boolean> {

		@SuppressWarnings("static-access")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			/*prg = ProgressDialog.show(context, "Please Wait",
					"Loading Images");*/
			// Log.i("uni_info", "Checking status updates...");
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			
			
		
			Log.i("log_info", "inbackground");
			int i;
			bitmapArray.clear();
			for(i = 0;i<iconUrl.size();i++){
				Bitmap btm = getImage(iconUrl.get(i),1);
				bitmapArray.add(btm);
				//btmArray.put(iconUrl.get(i), btm);
				
			}
			Log.i("log_info", "Size of btmArray is "+bitmapArray.size());
			return true;
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

			// Log.i("uni_info", "In onProgressUpdate..." + values[0]);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(prg.isShowing()){
			prg.cancel();
			prg.dismiss();
			}
			Log.i("log_info", "In onPostExecute..." + result);
			if (result == true) {
			Log.i("log_info", "Refreshing view...");
           
			grid.setAdapter(new MyAlbumGrid(context));
				
			}
		}
	}
	
	public class MySavedImageTask extends AsyncTask<Void, Void, Boolean> {

		@SuppressWarnings("static-access")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			prg = ProgressDialog.show(context, "Please Wait",
					"Loading saved Images");
			// Log.i("uni_info", "Checking status updates...");
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			
			bitmapArray.clear();
			
		//String root = Environment.getExternalStorageDirectory().toString() + "/saved_images";
		String root = Environment.getExternalStorageDirectory().getAbsolutePath()+"/saved_images";
		File file = new File(root);
		File[] files = file.listFiles();
		//Log.i("log_info", "no of saved images is  "+files.length);
			for(i = 0;i<files.length;i++){
				String path = files[i].getAbsolutePath();
				Log.i("log_info", "path is  "+path);
				Bitmap btm = getImage(path,2);
				Log.i("log_info", "bitmap is  "+btm);
				bitmapArray.add(btm);
				savedImageArray.add(files[i]);
				//btmArray.put(iconUrl.get(i), btm);
				
			}
			Log.i("log_info", "Size of btmArray is "+bitmapArray.size());
			return true;
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

			// Log.i("uni_info", "In onProgressUpdate..." + values[0]);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(prg.isShowing()){
			prg.cancel();
			prg.dismiss();
			}
			Log.i("log_info", "In onPostExecute..." + result);
			if (result == true) {
			Log.i("log_info", "Refreshing view...");
           
			grid.setAdapter(new MyAlbumGrid(context));
				
			}
		}
	}
	 public class MyAlbumGrid extends BaseAdapter {

			LayoutInflater mInflator;

			public MyAlbumGrid(Context c) {
				mInflator = LayoutInflater.from(c);
				// TODO Auto-generated constructor stub
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				
				//if(messageid.equals("savedPhotos")){
					return bitmapArray.size();
				/*}else{
					return iconUrl.size();
				}*/
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				if (convertView == null) {
					convertView = mInflator.inflate(R.layout.gridlayout, null);
					
				}
					appHolder.appImage = (ImageView) convertView
							.findViewById(R.id.grid_image);
					
				Log.i("log_info", "view position is "+position);
				
				
 
	            
			
	           appHolder.appImage.setImageBitmap(bitmapArray.get(position));
   
 			    
              appHolder.appImage.setOnClickListener(new OnClickListener() {
	
	           @Override
	           public void onClick(View v) {
		       // TODO Auto-generated method stub
		       Log.i("log_info", "image thumbnail clicked position is "+position);
		       if(messageid.equals("savedPhotos")){
		    	   showImage(position,savedImageArray.get(position).getAbsolutePath());
		       }else{
		       String iconURL = iconUrl.get(position);
		       String imageURL = imageUrl.get(iconURL); 
		       showImage(position,imageURL);
		       }
	           }
               });	
             
                convertView.setTag(appHolder);
				return convertView;
			
		}
	 }

		class AppHolder {
			ImageView appImage;
			
		}

		//wallpaper.setImageBitmap(btmArray.get(0));

		// TODO Auto-generated method stub
		
		private void showImage(final int position, String imageurl) {
			//Log.i("log_info","inside show unread message ..number is " +number);
			// TODO Auto-generated method stub
			imageposition = position;
			
			
			Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			//dialog.setCancelable(false);
			dialog.setContentView(R.layout.dialoglayout);
			
			
			
		
			
            final ImageView image = (ImageView)dialog.findViewById(R.id.dialog_image);
            RelativeLayout.LayoutParams imagelayoutParams = new RelativeLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT, height-100);
			image.setLayoutParams(imagelayoutParams);
			Button previous = (Button) dialog.findViewById(R.id.button_previous);
			Button next = (Button) dialog.findViewById(R.id.button_next);
			Button save = (Button) dialog.findViewById(R.id.button_save);
			
			if(messageid.equals("savedPhotos")){
				previous.setVisibility(View.INVISIBLE);
				next.setVisibility(View.INVISIBLE);
				save.setVisibility(View.INVISIBLE);
			}else{
				previous.setVisibility(View.VISIBLE);
				next.setVisibility(View.VISIBLE);
				save.setVisibility(View.VISIBLE);
			}
			 //image.setImageBitmap(btmArray.get(position));
			 //btm = getImage("http://1.bp.blogspot.com/-fCdkZvJUUDQ/UJQqKZSwhvI/AAAAAAAAGT4/prhh8KK4emk/s400/konark-sun-temple-orissa.jpg");
			/*try {
				new imageLoadTask().execute(imageurl).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			if(messageid.equals("savedPhotos")){
				btm = getImage(imageurl,2);
			}else{
			btm = getImage(imageurl,1);
			}
			//btm = Bitmap.createScaledBitmap(btm, 320, 480, true);	
			image.setImageBitmap(btm);
			next.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int localPosition = imageposition+1;
					imageposition = localPosition;
					
					if(localPosition <= bitmapArray.size()){
					String iconURL = iconUrl.get(localPosition);
				    String imageURL = imageUrl.get(iconURL); 
				    btm = getImage(imageURL,1);			
					image.setImageBitmap(btm);
					}
					else{
						imageposition = bitmapArray.size();
					Toast.makeText(context, "End of List", Toast.LENGTH_LONG).show();
					}
				}
			});

			previous.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int localPosition = imageposition-1;
					imageposition = localPosition;
					if(localPosition >= 0){
					String iconURL = iconUrl.get(localPosition);
				    String imageURL = imageUrl.get(iconURL); 
				    btm = getImage(imageURL,1);			
					image.setImageBitmap(btm);
					}else{
						imageposition = 0;
						Toast.makeText(context, "End of List", Toast.LENGTH_LONG).show();
					}
				}
			});

			save.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					saveImage(btm);
				}
			});

			dialog.show();
			
		}
		
		public void saveImage(Bitmap bitmap){
			String root = Environment.getExternalStorageDirectory().toString();
			File myDir = new File(root + "/saved_images");    
			myDir.mkdirs();
			Random generator = new Random();
			int n = 10000;
			n = generator.nextInt(n);
			String fname = "Image-"+ n +".jpg";
			File file = new File (myDir, fname);
			if (file.exists ()) file.delete (); 
			try {
			       FileOutputStream out = new FileOutputStream(file);
			       bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			       out.flush();
			       out.close();
			       Toast.makeText(context,"Image Saved Successfully",Toast.LENGTH_LONG).show();
			       Log.i("log_info", "image saved");

			} catch (Exception e) {
			       e.printStackTrace();
			       Log.i("log_info", "exception saving image" +e);
			}
		}

	public Bitmap getImage(String image_url, int id) {
		
		try {
			if(id ==1){
			url = new URL(image_url);
			}if(id == 2){
				try {
					uri = new URI(image_url);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if(id == 1){
			bmp = BitmapFactory.decodeStream(url.openConnection()
					.getInputStream());
			Log.i("log_info", "get bitmap is   "+bmp);		
			}if(id == 2){
				bmp = BitmapFactory.decodeFile(image_url);
				Log.i("log_info", "get bitmap is   "+bmp);		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bmp;
	}

	public class imageLoadTask extends AsyncTask<String, Void, Boolean> {

		@SuppressWarnings("static-access")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			prg = ProgressDialog.show(context, "Please Wait",
					"Loading Images");
			// Log.i("uni_info", "Checking status updates...");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			
			
		
			
			 btm = getImage(params[0],1);
				
				
			
			Log.i("log_info", "loading background url is "+params[0]);
			return true;
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

			// Log.i("uni_info", "In onProgressUpdate..." + values[0]);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(prg.isShowing()){
			prg.cancel();
			prg.dismiss();
			}
			Log.i("log_info", "In onPostExecute..." + result);
			if (result == true) {
			Log.i("log_info", "Refreshing view...");
           
			
				
			}
		}
	}

		
	}

