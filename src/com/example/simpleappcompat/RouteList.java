package com.example.simpleappcompat;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RouteList extends FragmentActivity implements LoaderCallbacks<RouteStorage>{
	ListView list;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.search);
		list = (ListView) findViewById(R.id.listview);
		getSupportLoaderManager().initLoader(3, null, this);
		
	}
	@Override
	public Loader<RouteStorage> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new AsyncRouteLoader(this,"");
	}
	@Override
	public void onLoadFinished(Loader<RouteStorage> arg0, RouteStorage data) {
		// TODO Auto-generated method stub
		if(data != null){
			LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View header_view = inflate.inflate(R.layout.list_item, null, false);
			View footer_view = inflate.inflate(R.layout.list_item, null, false);
			TextView tx = (TextView) header_view.findViewById(R.id.hotel_name);
			ImageView img = (ImageView) header_view.findViewById(R.id.imageview);
			img.setImageResource(R.drawable.starting);
			tx.setText("HeaderView");
			list.addHeaderView(header_view);
			
			RouteStorage route = data;
			List<String> list_data = route.maneur;
			Log.d("RouteLoadFinished", ""+list_data.size());
			MyRouteAdapter adapter = new MyRouteAdapter(this, R.layout.list_item, data);
			TextView txt = (TextView) footer_view.findViewById(R.id.hotel_name);
			txt.setText("FOoterView");
			list.addFooterView(footer_view);
			list.setAdapter(adapter);
		
		}else{
			Toast.makeText(getApplicationContext(), "Null Data Laded", Toast.LENGTH_SHORT).show();
			list.setVisibility(View.GONE);
			TextView text = (TextView) findViewById(R.id.list_visibility);
			text.setVisibility(View.VISIBLE);
		}
	}
		
	@Override
	public void onLoaderReset(Loader<RouteStorage> arg0) {
		// TODO Auto-generated method stub	
	}
	
	private class MyRouteAdapter extends ArrayAdapter<String>{
		Context context;
		RouteStorage routeen;
		List<String> datam_dir;
		List<String> datam_mave;
		List<String> datam_dis;
		List<String> datam_dur;
		public MyRouteAdapter(Context context, int resource, RouteStorage routeen) {
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
				v = inflater.inflate(R.layout.list_item, null);
			}
			txt_dir = (TextView) v.findViewById(R.id.hotel_name);
			txt_dis = (TextView) v.findViewById(R.id.hotel_address);
			txt_dur = (TextView) v.findViewById(R.id.hotel_email);
			img = (ImageView) v.findViewById(R.id.imageview);
			
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
}
