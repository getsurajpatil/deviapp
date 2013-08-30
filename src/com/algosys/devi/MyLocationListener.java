package com.algosys.devi;

import java.util.Locale;

import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

class MyLocationListener implements LocationListener {

   

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

 
	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		 Log.i("log_info", "inlocation change class");
       // editLocation.setText("");
       // pb.setVisibility(View.INVISIBLE);
        
        String longitude = "Longitude: " + loc.getLongitude();
        Log.i("log_info", longitude);
        String latitude = "Latitude: " + loc.getLatitude();
        Log.v("log_info", latitude);
        /*-------to get City-Name from coordinates -------- */
        String cityName = null;
        /*Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
            if (addresses.size() > 0)
                System.out.println(addresses.get(0).getLocality());
            cityName = addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
            + cityName;
        editLocation.setText(s);*/
    
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
