package com.algosys.devi;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.os.Environment;
import android.util.Log;

public class AudioRecorder {
	final MediaRecorder recorder = new MediaRecorder();
	final String path;

	/**
	 * 
	 * Creates a new audio recording at the given path
	 * 
	 * (relative to root of SD card).
	 */

	public AudioRecorder(String path) {

		// this.path = sanitizePath(path);

		this.path = path;
	}

	private String sanitizePath(String path) {

		if (!path.startsWith("/")) {

			path = "/" + path;

		}

		if (!path.contains(".")) {

			path += ".3gp";

		}

		return

		Environment.getExternalStorageDirectory().getAbsolutePath() +

		path;

	}

	/**
	 * 
	 * Starts a new recording.
	 */

	public void start() throws IOException {

                    

                    

                    

       String state =  android.os.Environment.getExternalStorageState();

      

        if(!state.equals(android.os.Environment.MEDIA_MOUNTED))  {

           throw new IOException("SD Card is not mounted. It is " + state + ".");

       }



       // make sure the directory we plan to store the recording in exists

       File directory = new File(path).getParentFile();

       if (!directory.exists() && !directory.mkdirs()) {

         throw new IOException("Path to file could not be created.");

       }



      

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);

      

        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

      

       recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

       recorder.setOutputFile(path);

       recorder.prepare();

       recorder.start();

       recorder.setOnErrorListener(l);

     

      

     }

	/**
	 * 
	 * Stops a recording that has been previously started.
	 */

	public void stop() throws IOException {

		recorder.stop();

		// recorder.release();

	}

	public OnErrorListener l = new OnErrorListener() {

		public void onError(MediaRecorder mr, int

		what, int extra)

		{

			Log.e("error in recording", "" + what

			+ " ,reason " + extra);

		}

	};

	public void recordAudio()

                   {

                     try

                     {

                                   MediaRecorder newRecorder = new

MediaRecorder(); 

                                  

newRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

                                  

newRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

                                  

newRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                                  

newRecorder.setOutputFile("/sdcard/testRecord.3gp");

                                   newRecorder.prepare();

                                   newRecorder.start();

                     }catch(Exception e)

                     {

                                     Log.e("exception in recordAudio",""+e);

                     }

                   }

	public int getMaxAmplitude() {

		return recorder.getMaxAmplitude();

	}

}
