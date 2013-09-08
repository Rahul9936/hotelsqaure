package com.example.simpleappcompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

@SuppressWarnings("rawtypes")
public class JsonPlacesLoader extends AsyncTaskLoader<HashMap>{
	
	String photo_ref;
	String textSearch;
	Context context;
	HashMap map, mData;
	public JsonPlacesLoader(Context context, String textSearch) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.textSearch = textSearch;
		map = new HashMap();
	}
	
	
	@SuppressWarnings("unchecked")
	private void parseJson(JSONObject json){
		try {
			final JSONObject results = json.getJSONArray("results").getJSONObject(0);
			final JSONObject geometry = results.getJSONObject("geometry");
			final JSONObject  location = geometry.getJSONObject("location");
			final JSONObject photo_reference = results.getJSONArray("photos").getJSONObject(0);
			String lat = location.getString("lat");
			String lng = location.getString("lng");
			photo_ref = photo_reference.getString("photo_reference");
			Log.d("JSON RESULT", lat + " " + lng);
			Log.d("JSON RESULT", photo_ref);
			map.put("lat", lat);
			map.put("lng", lng);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	Bitmap getBitmapFromURL(String src) {
		try {
			Log.e("src", src);
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			Log.e("Bitmap", "returned");
			return myBitmap;
		} catch (IOException e) {
			//e.printStackTrace();
			Log.e("Exception", "error on fetching image");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<Object, Object> loadInBackground() {
		// TODO Auto-generated method stub
		JsonRouteParser parser = new JsonRouteParser();
		
		JSONObject jobj = parser.getJSONFromUrl(textSearch);
		if(jobj != null){
			parseJson(jobj);
		}
		String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&photoreference=" + photo_ref + "&sensor=true&key=AIzaSyDYIwadNJY-EUJH5_r7QPW0xCLrTpBZ6_I";
		Bitmap bm = getBitmapFromURL(url);
		map.put("bmp", bm);
		Log.d("JSON URL", url);
		return map;
	}

	@Override
	public void deliverResult(HashMap data) {
		// TODO Auto-generated method stub
		if(isReset()){
			if(data != null){
				Log.d("DELIVER RESULT", "releasing resources");
			}
		}
		
		HashMap oldData = data;
		mData = oldData;
		super.deliverResult(data);
		
		if(isStarted())
			super.deliverResult(data);
		
		if (oldData != null) {
            Log.d("DELIVER RESULT", "releasing resources when oldData is null");
        }
	}

	@Override
	protected void onStartLoading() {
		// TODO Auto-generated method stub
		super.onStartLoading();
		if(mData != null){
			Log.d("mData", "Not null");
			deliverResult(mData);
		} else{
			Log.d("ONSTArt LOADING", "force load");
			forceLoad();
		}
	}
	
	@Override
	protected void onStopLoading() {
		// TODO Auto-generated method stub
		cancelLoad();
	}
	
	@Override
	public void onCanceled(HashMap data) {
		// TODO Auto-generated method stub
		super.onCanceled(data);
		Log.d("ON CANCELED", "canceled");
	}
	
	@Override
	protected void onReset() {
		// TODO Auto-generated method stub
		super.onReset();
		onStopLoading();
		if(mData != null)
			mData = null;
	}
}
