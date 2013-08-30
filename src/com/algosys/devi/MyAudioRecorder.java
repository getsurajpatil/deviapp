package com.algosys.devi;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class MyAudioRecorder {
final MediaRecorder recorder = new MediaRecorder();
MediaPlayer mMediaPlayer = new MediaPlayer();
String path;// = Environment.getExternalStorageDirectory().getAbsolutePath();
//String root = Environment.getExternalStorageDirectory().toString();
//String localpath;

  /**
   * Creates a new audio recording at the given path (relative to root of SD card).
   */
  public MyAudioRecorder(String path1) {
    this.path = path1;
    File myDir = new File(path + "/prayers");    
   	myDir.mkdirs();
   	path = path+"/prayers";
  }

  private String sanitizePath(String path) {
    if (!path.startsWith("/")) {
      path = "/" + path;
    }
    if (!path.contains(".")) {
      path += ".3gp";
    }
    return Environment.getExternalStorageDirectory().getAbsolutePath() + path;
  }

  /**
   * Starts a new recording.
   */
  public void start() throws IOException {
    String state = android.os.Environment.getExternalStorageState();
    if(!state.equals(android.os.Environment.MEDIA_MOUNTED))  {
        throw new IOException("SD Card is not mounted.  It is " + state + ".");
    }

    // make sure the directory we plan to store the recording in exists
   
    
	
	
   
	
	//File file = new File (myDir, fname);

	
    File directory = new File(path).getParentFile();
    // File[] n = directory.listFiles();
    //Log.i("log_info","number of prayers is "+n.length);
    if (!directory.exists() && !directory.mkdirs()) {
      throw new IOException("Path to file could not be created.");
    }
    Random generator = new Random();
    int n = 10000;
	n = generator.nextInt(n);
    String fname = Recorder.fileName+".3gp";
	path  =  path+"/"+fname;
    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    recorder.setOutputFile(path);
    recorder.prepare();
    recorder.start();
  }
  
  public void delete() throws IOException {
	  File file = new File(path);
	  boolean deleted = file.delete();
	  }
  
  /**
   * Stops a recording that has been previously started.
   */
  public void stop() throws IOException {
    recorder.stop();
    recorder.release();
  }
  
  public void play() throws IOException {
	 
      mMediaPlayer.setDataSource(path);
      mMediaPlayer.prepare();
      mMediaPlayer.start();
	  }
  
  public void pause() throws IOException {
	 
      mMediaPlayer.stop();
      
      mMediaPlayer.release();
	  }
  public void send() throws IOException {
		 
     //new sendPrayerTask.execute();
	  }
  
  public class sendPrayerTask extends AsyncTask<Void, Void, Boolean>{

	

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		File file = new File(path);
		try {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost("URL_WHERE_TO_UPLOAD");
		MultipartEntity entity = new MultipartEntity();
		
	    entity.addPart("myString", new StringBody( Recorder.fileName));
		
		
		entity.addPart("myAudioFile", new FileBody(file));
		httpost.setEntity(entity);
		HttpResponse response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	  
  }
}
