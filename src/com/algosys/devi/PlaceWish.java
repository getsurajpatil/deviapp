package com.algosys.devi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.algosys.devi.Wallpapers.imageLoadTask;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PlaceWish extends Activity {
	ImageView record,listen,requests,responses;
    Context context;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/prayers";
    ArrayList<String> listItems = new ArrayList<String>();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.placewish);
	    context = this;
	    record = (ImageView)findViewById(R.id.image_record);
	    listen = (ImageView)findViewById(R.id.image_listen);
	    requests = (ImageView)findViewById(R.id.image_requests);
	    responses = (ImageView)findViewById(R.id.image_response);
	    
	     record.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent rec = new Intent(PlaceWish.this,Recorder.class);
			startActivity(rec);
				
				
			}
		});
	    
        listen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

				   
				     
				        MediaPlayer mp = new MediaPlayer();
				        try {
				            mp.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath()+"/prayers/qwerty.3gp");
				            mp.prepare();
				            mp.start();
				        } catch (IOException e) {
				            //Log.e(LOG_TAG, "prepare() failed");
				        }
				   
				}

				/*private void stopPlaying() {
				    mp.release();
				    mp = null;
				}*/
			
		});
        requests.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		  
			showItemDialog(1);	
			}
		});
		responses.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
           showItemDialog(2);
			}
		});
	}

	    public void showItemDialog(int id){
	    	Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			//dialog.setCancelable(false);
			dialog.setContentView(R.layout.requestitemdialog);
			listItems.clear();
			
			
		
			
            final ListView items = (ListView)dialog.findViewById(R.id.request_item_list);
            if(id == 1){
        	
   		    File directory = new File(path);
   		    File[] n = directory.listFiles();
   		     Log.i("log_info", "file number is "+n.length);
   		    
   		    for(int i = 0;i<n.length;i++){
   		    listItems.add(n[i].getName());
   		    Log.i("log_info", "file name is "+n[i].getName());
   		    }
   		    /*ListAdapter adapter = new SimpleAdapter(this,
   			  items, 
   			android.R.layout.simple_list_item_1, 
   			  listItems, 
   			  new int[] { R.id.listview_grouplist });*/
   		    
   		    final StableArrayAdapter adapter = new StableArrayAdapter(this,
   		        android.R.layout.simple_list_item_1, listItems);
   		    items.setAdapter(adapter);
           }
            
            items.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int position, long arg3) {
					// TODO Auto-generated method stub
					 AlertDialog.Builder alertbox = new AlertDialog.Builder(PlaceWish.this);
			        	
			        	final String addOptions[] = { "Play using Media Player", "Delete"};
			        	
			        	alertbox.setItems(addOptions, new DialogInterface.OnClickListener() {
			        		
			    			public void onClick(DialogInterface dialog, int id) {
			    				switch (id) {
			    				case 1:
			    					/*String fileurl = path +"/"+listItems.get(position);

			    					Intent intentToPlayVideo = new Intent(Intent.ACTION_VIEW);
			    					intentToPlayVideo.setDataAndType(Uri.parse(fileurl), "video/*");
			    					startActivity(intentToPlayVideo);*/
			    					String fileurl1 = path +"/"+listItems.get(position);
			    					File file = new File(fileurl1);
			    					boolean deleted = file.delete();
			    					
			    					break;
			    					
			    				case 0:
			    					String fileurl = path +"/"+listItems.get(position);
			    					MediaPlayer mp = new MediaPlayer();
			    					try {
										mp.setDataSource(fileurl);
									} catch (IllegalArgumentException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IllegalStateException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
			    					try {
										mp.prepare();
									} catch (IllegalStateException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
			    					mp.start();
			    					break;
			    					
		                        default:
			    					break;
			    				}
			    			}

			    		});
			        	alertbox.setCancelable(true);
			    		alertbox.show();
					
				}
			});
			/*Button previous = (Button) dialog.findViewById(R.id.button_previous);
			Button next = (Button) dialog.findViewById(R.id.button_next);
			Button save = (Button) dialog.findViewById(R.id.button_save);*/
			
			 //image.setImageBitmap(btmArray.get(position));
			 //btm = getImage("http://1.bp.blogspot.com/-fCdkZvJUUDQ/UJQqKZSwhvI/AAAAAAAAGT4/prhh8KK4emk/s400/konark-sun-temple-orissa.jpg");
			

			dialog.show();
	}
	   
	    private class StableArrayAdapter extends ArrayAdapter<String> {

	        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	        public StableArrayAdapter(Context context, int textViewResourceId,
	            List<String> objects) {
	          super(context, textViewResourceId, objects);
	          for (int i = 0; i < objects.size(); ++i) {
	            mIdMap.put(objects.get(i), i);
	          }
	        }

	        @Override
	        public long getItemId(int position) {
	          String item = getItem(position);
	          return mIdMap.get(item);
	        }

	        @Override
	        public boolean hasStableIds() {
	          return true;
	        }

	      }
}


