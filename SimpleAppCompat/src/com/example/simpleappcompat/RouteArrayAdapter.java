package com.example.simpleappcompat;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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


	LayoutInflater inflater;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null){
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item_map, null);
			
			holder = new ViewHolder();
			holder.txt_address = (TextView) convertView.findViewById(R.id.map_place_name);
			holder.txt_email= (TextView) convertView.findViewById(R.id.map_distance);
			holder.txt_name = (TextView) convertView.findViewById(R.id.map_hotel_email);
			holder.txt4 = (TextView) convertView.findViewById(R.id.map_imageview);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Log.d("manuer", datam_mave.get(position));
		holder.txt_address.setTextSize(18);
		holder.txt_address.setText(Html.fromHtml(datam_dir.get(position)));
		holder.txt_email.setText(datam_dis.get(position));
		holder.txt_name.setText(datam_dur.get(position));
		holder.txt4.setText("" + position);
		return convertView;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datam_dir.size();
	}
}
