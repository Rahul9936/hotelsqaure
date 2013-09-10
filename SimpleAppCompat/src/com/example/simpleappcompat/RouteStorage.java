package com.example.simpleappcompat;

import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class RouteStorage {

	String startingPoint;
	String endPoint;
	String final_distance;
	String final_time;

	List<String> html_instruction;
	List<String> maneur;
	List<String> distance;
	List<String> time;
	List<LatLng> locations;
	List<LatLng> points;
	
	public RouteStorage(String startingPoint, String endPoint,
			String final_distance, String final_time,
			List<String> html_instruction, List<String> maneur,
			List<String> distance, List<String> time, List<LatLng> locations, List<LatLng> points) {
		super();
		this.startingPoint = startingPoint;
		this.endPoint = endPoint;
		this.final_distance = final_distance;
		this.final_time = final_time;
		this.html_instruction = html_instruction;
		this.maneur = maneur;
		this.distance = distance;
		this.time = time;
		this.locations = locations;
		this.points = points;
	}

	
}
