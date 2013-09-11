package com.example.simpleappcompat;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HotelFragment extends Fragment implements
		LoaderCallbacks<List<ClassGetter>>, OnItemClickListener,
		OnQueryTextListener {

	List<ClassGetter> mItems;
	ListView list;
	String s;
	String url;
	MyArrayAdapter adapter;
	List<ClassGetter> list_collection;
	View view;
	static boolean load_status = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.search, null, false);
		list = (ListView) view.findViewById(R.id.listview);

		url = getArguments().getString("url");
		setHasOptionsMenu(true);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		if (ListHolder.getList() == null) {
			Log.e("OnActivityCreated", "holder is null");
			getActivity().getSupportLoaderManager().initLoader(100, null, this);
		} else {
			Log.d("OnActivityCreated",
					"Control is in on Activity Created and holder is not null");
			load_status = true;
			LinearLayout layot = (LinearLayout) view
					.findViewById(R.id.linear_layout);
			layot.setVisibility(View.GONE);
			
			settingListAdapter(ListHolder.getList());
		}
	}
	
	private void settingListAdapter(List<ClassGetter> list_data){
		
		list.setVisibility(View.VISIBLE);
		adapter = new MyArrayAdapter(getActivity(), R.layout.list_item,
				list_data);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.showsearchlist, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);
		searchView.setOnQueryTextListener(this);
	}

	@Override
	public Loader<List<ClassGetter>> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new AsyncLoader(getActivity(), url);
	}

	private void showToast(String errorMsg) {
		/*LayoutInflater inflater = getActivity().getLayoutInflater();
		View layout = inflater.inflate(R.layout.cstom,
				(ViewGroup) view.findViewById(R.id.toast_layout_root));
		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText("No Internet Connection");

		Toast toast = new Toast(getActivity());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 200);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();*/
		
		LinearLayout linear_ = (LinearLayout) view.findViewById(R.id.text_visibility);
		TextView text_ = (TextView) view.findViewById(R.id.error_text);
		linear_.setVisibility(View.VISIBLE);
		text_.setText(errorMsg);
	}

	@Override
	public void onLoadFinished(Loader<List<ClassGetter>> arg0,
			List<ClassGetter> list_data) {
		// TODO Auto-generated method stub
		LinearLayout layot = (LinearLayout) view
				.findViewById(R.id.linear_layout);
		layot.setVisibility(View.GONE);
	
			ClassGetter statu = list_data.get(0);
			if(statu.status == 111){
				load_status = true;
				ListHolder.setList(list_data);
				settingListAdapter(list_data);
				LinearLayout linear_ = (LinearLayout) view.findViewById(R.id.text_visibility);
				linear_.setVisibility(View.GONE);
			}else if(statu.status == ViewHolder.data_not_found){
				Log.d("LoadStatus", ViewHolder.data_not_found + "data not found" );
				showToast("Gosh! No Hotel Found By this name ");
			}else{
				Log.d("LoadStatus", ViewHolder.no_internet + "no internet" );
				showToast("Opps! No Internet Connection");
			}
	}

	@Override
	public void onLoaderReset(Loader<List<ClassGetter>> arg0) {
		// TODO Auto-generated method stub

	}

	class MyArrayAdapter extends ArrayAdapter<ClassGetter> {

		List<ClassGetter> getter;

		ClassGetter c_getter;
		LayoutInflater inflater;
		private final Object mLock = new Object();
		Context context;
		MyFilter filler;

		public MyArrayAdapter(Context context, int textViewResourceId,
				List<ClassGetter> getter) {
			super(context, textViewResourceId, getter);
			// TODO Auto-generated constructor stub
			this.getter = getter;
			mItems = getter;
			this.context = context;

		}

		@Override
		public Filter getFilter() {
			// TODO Auto-generated method stub
			Log.e("Control", "in getFilter");
			if (filler == null)
				filler = new MyFilter();
			return filler;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			System.out.println("getCount" + mItems.size());
			return mItems.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			//View v = convertView;
			ViewHolder holder;
			if (convertView == null) {
				inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.list_item, null);
				
				holder = new ViewHolder();
				holder.txt_name = (TextView) convertView.findViewById(R.id.hotel_name);
				holder.txt_address = (TextView) convertView.findViewById(R.id.hotel_address);
				holder.txt_email = (TextView) convertView.findViewById(R.id.hotel_email);
				convertView.setTag(holder);
			} else{
				holder = (ViewHolder) convertView.getTag();
			}
			c_getter = mItems.get(position);

			holder.txt_name.setText(c_getter.name);
			holder.txt_address.setText(c_getter.state);
			holder.txt_email.setText(c_getter.type);
			// Log.d("Control", c_getter.name);
			return convertView;
		}

		private class MyFilter extends Filter {

			@SuppressLint("DefaultLocale")
			@Override
			protected FilterResults performFiltering(CharSequence prefix) {
				// TODO Auto-generated method stub
				// Initiate our results object
				Log.d("control", "in performFiltering");
				FilterResults results = new FilterResults();
				// If the adapter array is empty, check the actual items array
				// and use it
				if (mItems == null) {
					synchronized (mLock) {
						mItems = new ArrayList<ClassGetter>(getter);
						Log.d("mItems", "" + mItems.size());
					}
				}
				// No prefix is sent to filter by so we're going to send back
				// the original array
				if (prefix == null || prefix.length() == 0) {
					synchronized (mLock) {
						results.values = getter;
						results.count = getter.size();
					}
				} else {
					// Compare lower case strings
					String prefixString = prefix.toString().toLowerCase();
					// Local to here so we're not changing actual array
					final List<ClassGetter> items = mItems;
					final int count = items.size();
					final ArrayList<ClassGetter> newItems = new ArrayList<ClassGetter>(
							count);
					for (int i = 0; i < count; i++) {
						final ClassGetter item = items.get(i);
						final String itemName = item.name.toString()
								.toLowerCase();
						// First match against the whole, non-splitted value
						if (itemName.startsWith(prefixString)) {
							newItems.add(item);
						} else {
						}
					}
					// Set and return
					results.values = newItems;
					results.count = newItems.size();
				}
				return results;
			}

	
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				// TODO Auto-generated method stub
				Log.d("control", "in publish result");
				mItems = (List<ClassGetter>) results.values;
				// Let the adapter know about the updated list
				if (results.count > 0) {
					notifyDataSetChanged();
					Log.d("Dnotifuying", "NOTIFY DATASET CHANGE");
				} else {
					notifyDataSetInvalidated();
					Log.d("notifuying", "NOTIFY DATASET INVALIDATE");
				}
			}

		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), SearchDetail.class);
		intent.putExtra("Object", position);
		startActivity(intent);
		ListHolder.hotelFragment_hotelName = mItems.get(position).name;
		destroyLoader();
	}

	@Override
	public boolean onQueryTextChange(String queryText) {
		// TODO Auto-generated method stub
		Log.d("ON QUERY TEXT CHANGE", queryText);
		if (load_status)
			adapter.getFilter().filter(queryText);
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String queryText) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "text is " + queryText,
				Toast.LENGTH_SHORT).show();
		return false;
	}

	@SuppressWarnings("rawtypes")
	private void destroyLoader() {
		Loader loader = getActivity().getSupportLoaderManager().getLoader(100);
		if (loader != null && loader.isStarted())
			loader.stopLoading();
		Log.d("Loader", "Loader Has Been Stoped");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		load_status = false;
		Log.d("OnPause", "Control is in on Pause");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		destroyLoader();
		Log.d("OnDestroy", "on destroy is calling");
		// ListHolder.setList(null);
	}
}
