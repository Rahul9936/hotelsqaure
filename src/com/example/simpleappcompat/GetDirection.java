package com.example.simpleappcompat;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

public class GetDirection extends FragmentActivity implements LocationListener {

	LocationManager lm;
	String provider;
	TextView text;
	String locationProvider;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.sample);
		text = (TextView) findViewById(R.id.textVIew);
		getLocationData();
	}

	private void getLocationData() {

		lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		boolean enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!enabled) {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}

		Criteria criteria = new Criteria();
		provider = lm.getBestProvider(criteria, true);
		Log.d("provider", provider);
		Location location = lm.getLastKnownLocation(provider);
		if (location != null) {
			Log.d("Loacion", "" + location.getLatitude());
			text.setText("Lattitude is " + location.getLatitude() + "\n"
					+ "longitude is " + location.getLongitude());
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	    lm.requestLocationUpdates(provider, 0, 0, this);
	    DialogFragment newFragment = MyDialogFragment.newInstance();
	    newFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
	    
	    newFragment.show(getSupportFragmentManager(), "GetDirection");
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
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

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		int lat = (int) (location.getLatitude());
		int lng = (int) (location.getLongitude());

		Log.d("onLocationChanged", "Lattitude is " + lat + " longitude is "
				+ lng);
		text.setText("on Location Changed Lattitude is " + location.getLatitude() + "\n"
				+ "on Location Changed longitude is " + location.getLongitude());
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (lm != null)
			lm.removeUpdates(this);
	}
}
