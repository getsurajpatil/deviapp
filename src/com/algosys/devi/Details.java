package com.algosys.devi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Details extends Activity {
 ImageView locateMe;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	setContentView(R.layout.details);
	locateMe = (ImageView)findViewById(R.id.image_locate);
	
	locateMe.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/*Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:0,0?q=" + ("18.012473,76.066068")));
            try {
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
			
			Intent i = new Intent(Details.this,LocateMe.class);
			startActivity(i);
		}
	});
	    // TODO Auto-generated method stub
	}

}
