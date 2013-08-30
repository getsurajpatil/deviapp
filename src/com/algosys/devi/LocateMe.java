package com.algosys.devi;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LocateMe extends FragmentActivity implements LocationListener {
	ProgressDialog prg;
	Location location;
	LocationManager locationManager;
	Context context;
	String provider;
	GoogleMap googleMap;
	Button getDirections;
	LatLng myPosition;
	static final LatLng TULJAPUR = new LatLng(18.012473, 76.066068);
	Document doc;
	GMapV2Direction md = new GMapV2Direction();
	Button call, mail, sms;
	  String number = "+918793840741";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		// TODO Auto-generated method stub
		setContentView(R.layout.locateme);

		getDirections = (Button) findViewById(R.id.button_get_directions);
		call = (Button) findViewById(R.id.button_call);
		mail = (Button) findViewById(R.id.button_mail);
		sms = (Button) findViewById(R.id.button_sms);
		sms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
			}
		});
		mail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//intent.setType("text/plain");
				
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
			            "mailto","shanvikram2012@gmail.com", null));
			//	emailIntent.setType("message/rfc822");
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT");
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			}
		});
		call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				try{
				
			       
					Intent callIntent1 = new Intent(Intent.ACTION_CALL);
					callIntent1.setData(Uri.parse("tel:"+ number));
					startActivity(callIntent1);
				
				}catch(Exception e){
					
					
				}
			}	
		
		});

		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else { // Google Play Services are available

			// Getting reference to the SupportMapFragment of activity_main.xml
			SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map_layout);

			// Getting GoogleMap object from the fragment
			googleMap = fm.getMap();

			// Enabling MyLocation Layer of Google Map
			googleMap.setMyLocationEnabled(true);
			if (googleMap != null) {
				Marker hamburg = googleMap.addMarker(new MarkerOptions()
						.position(TULJAPUR).title("Tulja Bhavani Temple")/*
																		 * icon(
																		 * BitmapDescriptorFactory
																		 * .
																		 * fromResource
																		 * (R.
																		 * drawable
																		 * .
																		 * meditation
																		 * )
																		 */);

				googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
						TULJAPUR, 11));
				/*
				 * Marker kiel = googleMap.addMarker(new MarkerOptions()
				 * .position(KIEL) .title("Kiel") .snippet("Kiel is cool")
				 * .icon(BitmapDescriptorFactory
				 * .fromResource(R.drawable.ic_launcher)));
				 */
			}

		}
		getDirections.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// LatLng toPosition = new LatLng(13.683660045847258,
				// 100.53900808095932);

				// Getting LocationManager object from System Service
				// LOCATION_SERVICE
				LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

				// Creating a criteria object to retrieve provider
				Criteria criteria = new Criteria();

				// Getting the name of the best provider
				String provider = locationManager.getBestProvider(criteria,
						true);

				// Getting Current Location
				Location location = locationManager
						.getLastKnownLocation(provider);

				if (location != null) {
					onLocationChanged(location);
				}
				locationManager.requestLocationUpdates(provider, 20000, 0,
						LocateMe.this);
			}

		});

	}

	public class DocTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			prg = new ProgressDialog(LocateMe.this);
			prg.setTitle("Getting Directions");
			prg.setMessage("Please Wait..");
			prg.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.i("log_info", "inside doc background");
			doc = md.getDocument(myPosition, TULJAPUR,
					GMapV2Direction.MODE_DRIVING);
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (prg.isShowing()) {
				prg.cancel();
			}
			if (doc != null) {
				ArrayList<LatLng> directionPoint = md.getDirection(doc);
				PolylineOptions rectLine = new PolylineOptions().width(3)
						.color(Color.BLUE);

				for (int i = 0; i < directionPoint.size(); i++) {
					rectLine.add(directionPoint.get(i));
				}

				googleMap.addPolyline(rectLine);
				googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
						myPosition, 11));
				googleMap.animateCamera(CameraUpdateFactory.zoomTo(8));

			} else {
				Log.i("log_info", "doc null");
			}
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		// TextView tvLocation = (TextView) findViewById(R.id.tv_location);

		// Getting latitude of the current location
		double latitude = location.getLatitude();

		// Getting longitude of the current location
		double longitude = location.getLongitude();

		// Creating a LatLng object for the current location
		Log.i("log_info", "my position is " + latitude + " and " + longitude);
		myPosition = new LatLng(latitude, longitude);//
		// myPosition= new LatLng(18.506532,73.900799);
		new DocTask().execute();
		// Showing the current location in Google Map
		// // googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

		// Zoom in the Google Map
		// //googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

		// Setting latitude and longitude in the TextView tv_location
		// tvLocation.setText("Latitude:" + latitude + ", Longitude:"+ longitude
		// );

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
