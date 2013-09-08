package com.example.simpleappcompat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SearchFragment extends Fragment implements
		LoaderCallbacks<List<ClassGetter>> {

	MyPagerAdapter pagerAdapter;
	ViewPager viewPager;
	ListView list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.search, null, false);
		list = (ListView) view.findViewById(R.id.listview);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//getActivity().getSupportLoaderManager().initLoader(1, null, this);
	}

	@Override
	public Loader<List<ClassGetter>> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		Log.d("Loder", "in onCreate Loader");
		return new AsyncLoader(getActivity(),
				"http://hotelsquare.azurewebsites.net/hotel?id=UTTAR+PRADESH");
	}

	@Override
	public void onLoadFinished(Loader<List<ClassGetter>> arg0,
			List<ClassGetter> list_data) {
		// TODO Auto-generated method stub
		for(int i =0; i < list_data.size(); i++)
			Log.d("ONLOADFINISHED",(list_data.get(i)).name);
		MyArrayAdapter adapter = new MyArrayAdapter(getActivity(),
				R.layout.list_item, list_data);
		list.setAdapter(adapter);
	}

	@Override
	public void onLoaderReset(Loader<List<ClassGetter>> arg0) {
		// TODO Auto-generated method stub
		list.setAdapter(null);
	}

	class MyArrayAdapter extends ArrayAdapter<ClassGetter> {

	
		List<ClassGetter> getter;
		ClassGetter c_getter;
		Context context;
		//LayoutInflater inflater;
		TextView txt_name, txt_address, txt_email;
		public MyArrayAdapter(Context context, int textViewResourceId,
				List<ClassGetter> getter) {
			super(context, textViewResourceId, getter);
			// TODO Auto-generated constructor stub
			this.context = context;
			this.getter = getter;
			
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = convertView;
			
			if (v == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.list_item, null);
			}
				c_getter = getter.get(position);
				Log.d("ArrayAdapter", c_getter.name);
				
				txt_name = (TextView) v.findViewById(R.id.hotel_name);
				txt_address = (TextView) v.findViewById(R.id.hotel_address);
				txt_email = (TextView) v.findViewById(R.id.hotel_email);

				txt_name.setText(c_getter.name);
				txt_address.setText(c_getter.address);
				txt_email.setText(c_getter.email);
			
			return v;
		}

	}

}
