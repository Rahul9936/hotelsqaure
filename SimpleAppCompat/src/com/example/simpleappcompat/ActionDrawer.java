package com.example.simpleappcompat;

import java.util.List;

import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.simpleappcompat.MyLocation.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class ActionDrawer extends ActionBarActivity implements
		LoaderCallbacks<RouteStorage> {

	private GoogleMap mMap;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private LatLng hotel_loc;
	String hotel_name;

	private LatLng getLat() {
		String hotel_lat = getIntent().getStringExtra("hotel_lat");
		String hotel_lng = getIntent().getStringExtra("hotel_lng");
		if (hotel_lat != null)
			return new LatLng(Double.parseDouble(hotel_lat),
					Double.parseDouble(hotel_lng));
		else
			return null;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_drawer);

		hotel_loc = getLat();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
		R.string.app_name, /* "open drawer" description */
		R.string.app_label /* "close drawer" description */
		) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle("MAP");
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle("ROUTE");
			}
		};

		// mDrawerList.setAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, mPlanetTitles));
		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	};

	private void addMarkerToHotel() {
		hotel_name = getIntent().getStringExtra("hotel_name");

		if (mMap != null && hotel_loc != null) {
			try {
				mMap.addMarker(new MarkerOptions().position(hotel_loc).title(
						hotel_name));
				animateCam(hotel_loc, 8);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String buildQuery(Location location) {
		StringBuilder _builder = new StringBuilder("");
		_builder.append("http://maps.googleapis.com/maps/api/directions/json?origin=");
		_builder.append(location.getLatitude() + "," + location.getLongitude());
		_builder.append("&destination=");
		_builder.append(hotel_loc.latitude + "," + hotel_loc.longitude);
		_builder.append("&sensor=true");
		Log.d("QUERY", _builder.toString());
		return _builder.toString();
	}

	String query;

	private void getLocate() {
		LocationResult locationResult = new LocationResult() {
			@Override
			public void gotLocation(Location location) {
				// Got the location!
				try {
					Log.d("CallBack", "" + location.getLatitude());
					mMap.addMarker(new MarkerOptions().position(
							new LatLng(location.getLatitude(), location
									.getLongitude())).title("MyLocation"));
					query = buildQuery(location);
					ActionDrawer.this.getSupportLoaderManager().initLoader(
							1000, null, ActionDrawer.this);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
		};
		MyLocation myLocation = new MyLocation();
		if (myLocation.getLocation(this, locationResult)) {
			Log.d("LOCATION OF NEW", "location found");
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		SupportMapFragment mf = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mMap = mf.getMap();
		getLocate();
		if (query != null) {
			Log.d("QUERY", "" + query);
		}
		addMarkerToHotel();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	@Override
	public Loader<RouteStorage> onCreateLoader(int arg0, Bundle arg1) {

		return new AsyncRouteLoader(this, query);
	}

	private void animateCam(LatLng loc, int zooom) {
		Log.d("LOCATION IS ", loc.latitude + " " + loc.longitude);
		mMap.animateCamera(
				CameraUpdateFactory.newLatLngZoom(loc, (float) zooom), 2000,
				null);
	}

	private void setList(RouteStorage data) {
		RouteArrayAdapter adapter = new RouteArrayAdapter(this,
				R.layout.list_item, data);
		mDrawerList.setAdapter(adapter);
	}

	@Override
	public void onLoadFinished(Loader<RouteStorage> arg0, RouteStorage data) {
		// TODO Auto-generated method stub
		List<LatLng> latlng;
		PolylineOptions rectOptions = new PolylineOptions();
		if (data != null) {
			latlng = data.points;
			for (int i = 0; i < latlng.size(); i++) {
				rectOptions.add(latlng.get(i)).color(Color.BLUE);
			}
			String finalDis = data.final_distance;
			int ZOOM;
			double ad = Double.parseDouble(finalDis) / 1000;
			if (ad > 100)
				ZOOM = 10;
			else
				ZOOM = 12;
			mMap.addPolyline(rectOptions);
			int animateTo = data.locations.size() / 2;
			animateCam(data.locations.get(animateTo), ZOOM);
			setList(data);
		} else {
			Toast.makeText(
					getApplicationContext(),
					"You have requested for distance more than 300km or Check your internet connection",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onLoaderReset(Loader<RouteStorage> arg0) {
		// TODO Auto-generated method stub

	}
}
