package com.example.simpleappcompat;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class AsyncRouteLoader extends AsyncTaskLoader<RouteStorage> {

	String url;
	RouteStorage tempRouteStorage;
	public AsyncRouteLoader(Context context, String url) {
		super(context);
		// TODO Auto-generated constructor stub
		this.url = url;
	}

	@Override
	public RouteStorage loadInBackground() {
		// TODO Auto-generated method stub
		RouteStorage route = null;
		JsonRouteParser parserJSON = new JsonRouteParser();
		try{
		
		JSONObject parsedObj = parserJSON.getJSONFromUrl(url);
			route = processJSON(parsedObj);
		}catch(NullPointerException e ){
			e.printStackTrace();
			return null;
		}
		return route;
	}

	private List<LatLng> decodePoly(String encoded) {

	    List<LatLng> poly = new ArrayList<LatLng>();
	    int index = 0, len = encoded.length();
	    int lat = 0, lng = 0;

	    while (index < len) {
	        int b, shift = 0, result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lat += dlat;

	        shift = 0;
	        result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lng += dlng;

	        LatLng p = new LatLng( (((double) lat / 1E5)),
	                 (((double) lng / 1E5) ));
	        poly.add(p);
	    }

	    return poly;
	}
	
	private RouteStorage processJSON(JSONObject parsedObj) {
		try {
			String starting_point, end_point, final_distance, final_time;

			final JSONObject jsonRoute = parsedObj.getJSONArray("routes")
					.getJSONObject(0);
			final JSONObject leg = jsonRoute.getJSONArray("legs")
					.getJSONObject(0);
			final JSONObject dis = leg.getJSONObject("distance");
			final JSONObject dur = leg.getJSONObject("duration");
			final JSONObject polyLine = jsonRoute.getJSONObject("overview_polyline");
			
			String points = polyLine.getString("points");
			List<LatLng> list_points = decodePoly(points);
			
			starting_point = leg.getString("start_address");
			end_point = leg.getString("end_address");
			final_distance = dis.getString("value");
			final_time = dur.getString("text");
			
			String lenght_value = dis.getString("value");
			if(Double.parseDouble(lenght_value) > 300000) return null;

			Log.d("Final", starting_point + "\n" + end_point + "\n"
					+ final_distance + "\n" + final_time);

			List<String> html_ins, man, distance_list, time_list;
			List<LatLng> locations = new ArrayList<LatLng>();
			html_ins = new ArrayList<String>();
			man = new ArrayList<String>();
			distance_list = new ArrayList<String>();
			time_list = new ArrayList<String>();
			
			String lat, lng;
			
			final JSONArray steps = leg.getJSONArray("steps");
			for (int i = 0; i < steps.length(); i++) {
				final JSONObject step = steps.getJSONObject(i);
				JSONObject s_dis = step.getJSONObject("distance");
				JSONObject s_time = step.getJSONObject("duration");
				JSONObject startLoc = step.getJSONObject("start_location");
				
				
				if(i == steps.length()-1){
					JSONObject obj = step.getJSONObject("end_location");
					lat = obj.getString("lat");
					lng = obj.getString("lng");
				}else{
					lat = startLoc.getString("lat");
					lng = startLoc.getString("lng");
				}
				
				locations.add(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
				
				html_ins.add(step.getString("html_instructions"));
				distance_list.add(s_dis.getString("text"));
				time_list.add(s_time.getString("text"));

				if (step.has("maneuver")) {
					man.add(step.getString("maneuver"));
				} else {
					man.add("");
				}
			}
			
			RouteStorage storage = new RouteStorage(starting_point, end_point,
					final_distance, final_time, html_ins, man, distance_list,
					time_list, locations, list_points);
			return storage;
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deliverResult(RouteStorage data) {
		// TODO Auto-generated method stub
		if(isReset()){
			if(data != null){
				Log.d("DELIVER RESULT", "releasing resources");
			}
		}
		
		RouteStorage oldData = data;
		tempRouteStorage = oldData;
		
		if(isStarted())
			super.deliverResult(data);
		
		if (oldData != null) {
            Log.d("DELIVER RESULT", "releasing resources when oldData is null");
        }
	}

	@Override
	protected void onStartLoading() {
		// TODO Auto-generated method stub
		if(tempRouteStorage != null){
			deliverResult(tempRouteStorage);
		} else{
			forceLoad();
		}
	}
	
	@Override
	protected void onStopLoading() {
		// TODO Auto-generated method stub
		cancelLoad();
	}
	
	@Override
	public void onCanceled(RouteStorage data) {
		// TODO Auto-generated method stub
		super.onCanceled(data);
		Log.d("ON CANCELED", "canceled");
	}
	
	@Override
	protected void onReset() {
		// TODO Auto-generated method stub
		super.onReset();
		onStopLoading();
		if(tempRouteStorage != null)
			tempRouteStorage = null;
	}
}
