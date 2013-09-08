package com.example.simpleappcompat;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;


public class GpsLocationFinder {
	
	LocationManager lm;
	Context context;
	String provider;
	Location location;
	public GpsLocationFinder(Context context) {
		super();
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public Location getLocation(){
		lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		boolean enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!enabled) {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			context.startActivity(intent);
		}
		
		Criteria criteria = new Criteria();
		provider = lm.getBestProvider(criteria, true);
		Location location = lm.getLastKnownLocation(provider);
		if (location != null) {
			Log.d("Loacion", "" + location.getLatitude());
		}
		lm.requestLocationUpdates(provider, 1000, 0, new Location_Listener());
		
		if(this.location != null)
			return this.location;
		else return null;
	}
	
	
	
	class Location_Listener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			GpsLocationFinder.this.location = location;
			if(location != null){
				lm.removeUpdates(this);
				Log.d("GPS", ""+location.getLatitude());
				Log.d("GPS", ""+location.getLongitude());
			}
			Log.d("GPS LOCATION LISTENER", "IN GPAS LISTENER");
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
}
