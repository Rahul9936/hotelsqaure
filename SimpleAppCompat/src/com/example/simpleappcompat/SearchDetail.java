package com.example.simpleappcompat;

import java.util.HashMap;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

@SuppressWarnings("rawtypes")
public class SearchDetail extends ActionBarActivity implements
		LoaderCallbacks<HashMap>, OnClickListener {

	ImageView img;
	String hotel_name, hotel_city, hotel_phone, hotel_email;
	String hotel_lat, hotel_lng;
	boolean check = false;
	ImageButton btn_map, btn_call, btn_email;

	private void setTextT(ClassGetter get) {

		hotel_city = get.city;
		hotel_name = get.name;
		hotel_phone = get.phone;
		String hotel_state = get.state;
		String hotel_address = get.address;
		hotel_email = get.email;

		((TextView) findViewById(R.id.i_hotel_name)).setText(hotel_name);
		((TextView) findViewById(R.id.i_hotel_address)).setText(hotel_address);
		((TextView) findViewById(R.id.i_contact)).setText(hotel_phone);
		((TextView) findViewById(R.id.i_email_id)).setText(hotel_email);
		((TextView) findViewById(R.id.i_state)).setText(hotel_state);
		((TextView) findViewById(R.id.i_star)).setText(get.type);
		((TextView) findViewById(R.id.i_rooms)).setText(get.room + " rooms");
		((TextView) findViewById(R.id.i_fax)).setText(get.fax);
		((TextView) findViewById(R.id.i_website)).setText(get.website);
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.interactive);
		int position = getIntent().getIntExtra("Object", 0);
		ClassGetter get = ListHolder.getListAtPos(position);
		setTitle("Hotel");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
				|| !ListHolder.searhDetail_hotelName
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
				check = true;
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean checkForGooglePlaserVices() {
		// TODO Auto-generated method stub

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d("Location Updates", "Google Play services is available.");
			// Continue
			return true;
			// Google Play services was not available for some reason
		} else {
			// Get the error code
			int errorCode = ConnectionResult.SERVICE_MISSING;
			// Get the error dialog from Google Play services
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					errorCode, this, 9000);

			// If Google Play services can provide an error dialog
			if (errorDialog != null) {
				// Create a new DialogFragment for the error dialog
				errorDialog.show();
				/*MyDialogFragment errorFragment = MyDialogFragment.newInstance();
				errorFragment.setDialog(errorDialog);
				errorFragment.show(getSupportFragmentManager(),
						"Location Updates");*/
			}
		}
		return false;
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
			Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_EMAIL, new String[]{hotel_email});
			email.putExtra(Intent.EXTRA_SUBJECT, "subject");
			email.putExtra(Intent.EXTRA_TEXT, "Hello Buddy How are you");
			email.setType("message/rfc822");
			startActivity(Intent.createChooser(email,
					"Choose an Email client :"));
			break;
		case R.id.inter_map:

			Intent inter = new Intent(SearchDetail.this, ActionDrawer.class);
			if (hotel_lat != null) {
				inter.putExtra("hotel_lat", hotel_lat);
				inter.putExtra("hotel_lng", hotel_lng);
				inter.putExtra("hotel_name", hotel_name);
			}
			if (check) {
				if(checkForGooglePlaserVices())
					startActivity(inter);

			} else {
				MyDialogFragment frag = MyDialogFragment.newInstance();
				frag.show(getSupportFragmentManager(), "TAG");
			}
			break;
		}
	}

}
