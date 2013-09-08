package com.example.simpleappcompat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationArrayAdapter extends ArrayAdapter<String> {

	Context ctx;
	String[] array;
	int[] img_array;

	public NavigationArrayAdapter(Context context, int resource,
			int textViewResourceId, String[] arg) {
		super(context, resource, textViewResourceId, arg);
		// TODO Auto-generated constructor stub
		this.ctx = context;
		array = new String[] { "Email", "Contact", "Map", "Website" };
		img_array = new int[] { R.drawable.email, R.drawable.call,
				R.drawable.map, R.drawable.direction };
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	TextView text;
	ImageView img;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.navigation, null);
		}
		Log.d("arrayAdapter", array[position]);
		text = (TextView) view.findViewById(R.id.navi_text);
		img = (ImageView) view.findViewById(R.id.navi_img);
		text.setText(array[position]);
		img.setImageResource(img_array[position]);
		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.navigation, null);
		}
		Log.d("arrayAdapter", array[position]);
		text = (TextView) view.findViewById(R.id.navi_text);
		img = (ImageView) view.findViewById(R.id.navi_img);
		text.setText(array[position]);
		img.setImageResource(img_array[position]);
		return view;
	}
}
