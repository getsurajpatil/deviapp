package com.algosys.devi;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.algosys.devi.Wallpapers.imageLoadTask;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Recorder extends Activity implements Runnable {

	/** Called when the activity is first created. */
	SeekBar seek;
	/*CheckBox start;
	TextView starttext;*/
	ToggleButton record;
	Button reset,play,send;
    AudioRecorder ar;
    MyAudioRecorder mar;
    static boolean stop = false;
    TextView startText,finishText,titleText;
    static String fileName;
   

    String path = Environment.getExternalStorageDirectory().getAbsolutePath();

    Context context;

    Uri uri;
    Thread progressThread;
    File file ;
    int currentPosition= 0;
    int total = 60000;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.recorder);
	    seek = (SeekBar)findViewById(R.id.seekBar);
	    reset = (Button)findViewById(R.id.button_reset);
	    play = (Button)findViewById(R.id.button_play);
	   send = (Button)findViewById(R.id.button_send);
	  // startText = (TextView)findViewById(R.id.text_start);
	   //finishText = (TextView)findViewById(R.id.text_finish);
	   titleText = (TextView)findViewById(R.id.text_selectpuja);
	   
	   //startText.setText("0");
       //finishText.setText("60");
	   
	   /* start = (CheckBox)findViewById(R.id.start);
	    starttext = (TextView)findViewById(R.id.check_text);
	    starttext.setText("Start");*/
	    context = this;
	
      // uri = Uri.parse(path);
       // ar = new AudioRecorder(path);
	    showDescriptionDialog();
	    reset.setText("Delete/New");
        mar = new MyAudioRecorder(path);
	    record = (ToggleButton)findViewById(R.id.toggleButton1);
	    
	    record.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					try {

						// ar = new AudioRecorder(path);

						// smp = new Sampling(ar);

						 mar.start();
						 stop = false;
						 seek.setProgress(0);
				         seek.setMax(60000);
				         currentPosition = 0;
				         new Thread((Runnable) context).start();
	                      play.setClickable(false);
	                      reset.setClickable(false);
	                      send.setClickable(false);
						// ap.reset();

					} catch (IOException e) {

						// TODO Auto-generated catch block

						Log.e("Exception instart", "" + e);

					}
		
				}else{
					
					try {

						mar.stop();
						stop = true;
						 play.setClickable(true);
	                     reset.setClickable(true);
	                     send.setClickable(true);
						 record.setClickable(false);
						seek.setProgress(seek.getProgress());

					} catch (IOException e) {

						// TODO Auto-generated catch block

						e.printStackTrace();

					}
				}
			}
		});
	    
	   reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					mar.delete();
					seek.setProgress(0);
					Intent intent = getIntent();
					finish();
					startActivity(intent);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
      play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					mar.play();
					 stop = false;
					
					 seek.setProgress(0);
			         seek.setMax(currentPosition);
			        // finishText.setText(currentPosition);
					 currentPosition = 0;
					// startText.setText("0");
			        
					 new Thread((Runnable) context).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
      send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//mar.send();
				NotificationManager notifier;
				Notification notifyObj = null;
				// Context context = null;

				// context = getApplicationContext();
				notifier = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);

				 Intent notificationIntent = new Intent(context, PlaceWish.class);
				PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
						notificationIntent, Notification.FLAG_AUTO_CANCEL);
				notifyObj = new Notification(R.drawable.meditation, "New Response",
						System.currentTimeMillis());
				
				notifyObj.setLatestEventInfo(context, "mBhakti",
						"You got a new response to your prayer", contentIntent);
				notifyObj.flags |= Notification.FLAG_AUTO_CANCEL;
				notifier.notify(1, notifyObj);
				finish();
			}
		});
	    // TODO Auto-generated method stub
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stop = true;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
        while (!stop && currentPosition<total) {
            try {
            	Log.i("log_info", "inside thread" +currentPosition);
                Thread.sleep(1000);
                currentPosition +=1000 ;
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }            
           seek.setProgress(currentPosition);
           //startText.setText(currentPosition);
           //finishText.setText(60-currentPosition);
        }
    }
	
	 public void showDescriptionDialog(){
		    final Dialog dialog = new Dialog(this);
			//dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			//dialog.setCancelable(false);
		    Window window = dialog.getWindow();
			window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		    dialog.setTitle("Prayer Description");
			dialog.setContentView(R.layout.prayerdescriptiondialog);
			
			
			
		
			
      
			final EditText name = (EditText) dialog.findViewById(R.id.edit_prayer_name);
		    Button save = (Button) dialog.findViewById(R.id.button_save);
		    Button cancel = (Button) dialog.findViewById(R.id.button_cancel);
			
			 //image.setImageBitmap(btmArray.get(position));
			 //btm = getImage("http://1.bp.blogspot.com/-fCdkZvJUUDQ/UJQqKZSwhvI/AAAAAAAAGT4/prhh8KK4emk/s400/konark-sun-temple-orissa.jpg");
			
			save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if( name.getText().toString() != ""){
					fileName = name.getText().toString();
					titleText.setText(fileName);
					dialog.dismiss();
					}
					else{
						Toast.makeText(context,"Please Enter Prayer Name", Toast.LENGTH_SHORT).show();
					}
				}
			});

			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					finish();
				}
			});


			dialog.show();
		    
	 }
	}


