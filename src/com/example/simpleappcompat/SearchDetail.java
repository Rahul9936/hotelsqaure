package com.example.simpleappcompat;

import java.util.HashMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

@SuppressWarnings("rawtypes")
public class SearchDetail extends ActionBarActivity implements
		LoaderCallbacks<HashMap>, OnClickListener {

	ImageView img;
	String hotel_name, hotel_city, hotel_phone;
	String hotel_lat, hotel_lng;
	boolean check = false;
	ImageButton btn_map, btn_call, btn_email;

	private void setTextT(ClassGetter get) {

		hotel_city = get.city;
		hotel_name = get.name;
		hotel_phone = get.phone;
		String hotel_state = get.state;
		String hotel_address = get.address;
		String hotel_email = get.email;

		((TextView) findViewById(R.id.i_hotel_name)).setText(hotel_name);
		((TextView) findViewById(R.id.i_hotel_address)).setText(hotel_address);
		((TextView) findViewById(R.id.i_contact)).setText(hotel_phone);
		((TextView) findViewById(R.id.i_email_id)).setText(hotel_email);
		((TextView) findViewById(R.id.i_state)).setText(hotel_state);
		((TextView) findViewById(R.id.i_star)).setText(get.type);
		((TextView) findViewById(R.id.i_rooms)).setText(get.room + " rooms");
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.interactive);
		int position = getIntent().getIntExtra("Object", 0);
		ClassGetter get = ListHolder.getListAtPos(position);

		if (get != null) {

			Log.d("SEARCH DETAIL", "Name is in extra class " + get.name);
		} else
			Log.d("SEARCHDETAIL", "OBJECT IS NULL");

		setTextT(get);
		initialize();

	}

	private void initialize() {

		img = (ImageView) findViewById(R.id.i_img);
		btn_call = (ImageButton) findViewById(R.id.inter_call);
		btn_email = (ImageButton) findViewById(R.id.inter_email);
		btn_map = (ImageButton) findViewById(R.id.inter_map);

		btn_call.setOnClickListener(this);
		btn_email.setOnClickListener(this);
		btn_map.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (ListHolder.searhDetail_hotelName.equals("")
				|| ! ListHolder.searhDetail_hotelName
						.equals(ListHolder.hotelFragment_hotelName))
			getSupportLoaderManager().initLoader(400, null, this);
		else {
			HashMap m = ListHolder.mapData;
			hotel_lat = m.get("lat").toString();
			hotel_lng = m.get("lng").toString();
			
			Bitmap bmps = (Bitmap) m.get("bmp");
			try {
				img.setImageBitmap(bmps);
				img.setVisibility(View.VISIBLE);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.search_detail, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.item_call:
			String url = "tel:" + hotel_phone;
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
			startActivity(intent);
			break;
		case R.id.item_email:
			Intent inter = new Intent(SearchDetail.this, ActionDrawer.class);
			if (hotel_lat != null) {
				inter.putExtra("hotel_lat", hotel_lat);
				inter.putExtra("hotel_lng", hotel_lng);
				inter.putExtra("hotel_name", hotel_name);
			}
			if (check)
				startActivity(inter);
			else {
				MyDialogFragment frag = MyDialogFragment.newInstance();
				frag.show(getSupportFragmentManager(), "TAG");
			}
			break;
		case R.id.item_direction:
			Intent in = new Intent(SearchDetail.this, RouteList.class);
			startActivity(in);
			break;
		case R.id.item_map:

			boolean check = checkForGooglePlaserVices();
			Intent inte;
			if (check) {
				inte = new Intent(SearchDetail.this, GetDirection.class);
				startActivity(inte);
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private boolean checkForGooglePlaserVices() {
		// TODO Auto-generated method stub
		switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)) {
		case ConnectionResult.SUCCESS:
			Toast.makeText(getApplicationContext(), "APK IS INSTALLED",
					Toast.LENGTH_SHORT).show();
			return true;
		default:
			return false;
		}

	}

	private void destroyLoader() {
		Loader loader = getSupportLoaderManager().getLoader(400);
		if (loader != null && loader.isStarted())
			loader.stopLoading();
		Log.d("Loader", "Loader Has Been Stoped");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		destroyLoader();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		destroyLoader();
	}

	@Override
	public Loader<HashMap> onCreateLoader(int arg0, Bundle bndl) {
		// TODO Auto-generated method stub
		String query = hotel_name + " " + hotel_city;
		query = query.replace(" ", "+");
		String textSearch = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="
				+ query
				+ "&sensor=true&key=AIzaSyDYIwadNJY-EUJH5_r7QPW0xCLrTpBZ6_I";
		// Log.d("PLACE URL IS", textSearch);
		return new JsonPlacesLoader(this, textSearch);
	}

	@Override
	public void onLoadFinished(Loader<HashMap> arg0, HashMap map) {
		// TODO Auto-generated method stub

		Bitmap bmps = null;
		if (map != null)
			try {
				check = true;

				Log.d("HOTEL LATTITUDE", map.get("lat").toString());
				Log.d("HOTEL LONGITUDE", map.get("lng").toString());

				bmps = (Bitmap) map.get("bmp");
				try {
					img.setImageBitmap(bmps);
					img.setVisibility(View.VISIBLE);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				ListHolder.mapData = map;
				ListHolder.searhDetail_hotelName = hotel_name;
				hotel_lat = map.get("lat").toString();
				hotel_lng = map.get("lng").toString();
			} catch (NullPointerException e) {
				// e.printStackTrace();
				System.out.println("Image Can not loaded");
			}
	}

	@Override
	public void onLoaderReset(Loader<HashMap> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.inter_call:
			String url = "tel:" + hotel_phone;
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
			startActivity(intent);
			break;
		case R.id.inter_email:
			break;
		case R.id.inter_map:

			Intent inter = new Intent(SearchDetail.this, ActionDrawer.class);
			if (hotel_lat != null) {
				inter.putExtra("hotel_lat", hotel_lat);
				inter.putExtra("hotel_lng", hotel_lng);
				inter.putExtra("hotel_name", hotel_name);
			}
			if (check)
				startActivity(inter);
			else {
				MyDialogFragment frag = MyDialogFragment.newInstance();
				frag.show(getSupportFragmentManager(), "TAG");
			}
			break;
		}
	}
}
