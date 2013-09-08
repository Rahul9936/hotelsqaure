package com.example.simpleappcompat;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RouteArrayAdapter extends ArrayAdapter<String>{

	Context context;
	RouteStorage routeen;
	List<String> datam_dir;
	List<String> datam_mave;
	List<String> datam_dis;
	List<String> datam_dur;
	public RouteArrayAdapter(Context context, int resource, RouteStorage routeen) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.routeen = routeen;
		datam_dir = routeen.html_instruction;
		datam_mave = routeen.maneur;
		datam_dis = routeen.distance;
		datam_dur = routeen.time;
	}

	TextView txt_dir, txt_dis, txt_dur;
	ImageView img;
	LayoutInflater inflater;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		if(v == null){
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_item_map, null);
		}
		txt_dir = (TextView) v.findViewById(R.id.map_place_name);
		txt_dis = (TextView) v.findViewById(R.id.map_distance);
		txt_dur = (TextView) v.findViewById(R.id.map_hotel_email);
		img = (ImageView) v.findViewById(R.id.map_imageview);
		
		Log.d("manuer", datam_mave.get(position));
		txt_dir.setTextSize(18);
		txt_dir.setText(Html.fromHtml(datam_dir.get(position)));
		txt_dis.setText(datam_dis.get(position));
		String s = datam_mave.get(position);
		if(s.equalsIgnoreCase("turn-left")){
			img.setImageResource(R.drawable.turnleft);
		}else if(s.equalsIgnoreCase("turn-right")){
			img.setImageResource(R.drawable.turnright);
		}
		return v;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datam_dir.size();
	}
}
