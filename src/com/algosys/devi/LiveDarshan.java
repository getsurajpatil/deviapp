package com.algosys.devi;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class LiveDarshan extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.livedarshan);
	    try{
	  String link="http://s1133.photobucket.com/user/Anniebabycupcakez/media/1489568335355_1995.mp4.html";
        VideoView videoView = (VideoView) findViewById(R.id.video_live);
       /// videoView.getSettings().setJavaScriptEnabled(true);
       // videoView.loadUrl(link);
       // MediaController mediaController = new MediaController(this);
        //mediaController.setAnchorView(videoView);
       // Uri video = Uri.parse(link);
       /* videoView.setMediaController(mediaController);
        videoView.setVideoURI(video);
        videoView.start();*/
        //mVideoView = (VideoView) findViewById(R.id.surface_view);
        videoView.setVideoPath("rtsp://192.168.0.100:5544/");
        videoView.setMediaController(new MediaController(this));
        videoView.start();
    } catch (Exception e) {
        // TODO: handle exception
        Toast.makeText(this, "Error connecting", Toast.LENGTH_SHORT).show();
    }

	    // TODO Auto-generated method stub
	    //VideoView video = (VideoView)findViewById(R.id.video_live);
	    
	}

}
