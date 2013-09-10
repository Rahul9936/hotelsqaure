package com.example.simpleappcompat;

import java.util.HashMap;
import java.util.List;

import android.util.Log;

class ListHolder {

	static int position = 0;
	static List<ClassGetter> list_hotel;
	static List<AgentGetter> list_agent;
	@SuppressWarnings("rawtypes")
	static HashMap mapData;
	static String hotelFragment_hotelName = "";
	static String searhDetail_hotelName = "";

	public static List<ClassGetter> getList() {
		return list_hotel;
	}

	public static ClassGetter getListAtPos(int position) {
		return list_hotel.get(position);
	}

	public static void setList(List<ClassGetter> list) {
		list_hotel = list;
		System.out.println("Holder is called");
		for (int i = 0; list != null && i < list.size(); i++)
			Log.d("HOLDER", list.get(i).name + "");
	}

	public static void setAgentList(List<AgentGetter> list) {
		list_agent = list;
		System.out.println("Agent is called");
	}
	
	public static List<AgentGetter> getAgentList() {
		return list_agent;
	}
	
	public static AgentGetter getAgentAtPosition(int i){
		return list_agent.get(i);
	}

	@SuppressWarnings("rawtypes")
	public static void setPlaceData(HashMap map) {
		mapData = map;
	}

	@SuppressWarnings("rawtypes")
	public static HashMap getPlaceData() {
		return mapData;
	}
}
