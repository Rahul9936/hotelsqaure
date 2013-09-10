package com.example.simpleappcompat;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AgentFragment extends Fragment implements
		LoaderCallbacks<List<AgentGetter>>, OnItemClickListener {

	ListView list;
	String url;
	View view;
	MyAgentAdapter adapter;
	List<AgentGetter> list_collection;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		// url = getArguments().getString("url");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.search, null, false);
		list = (ListView) view.findViewById(R.id.listview);
		TextView t = new TextView(getActivity());
		t.setText("HELLO AGENT");
		list.setEmptyView(t);
		url = getArguments().getString("url");
		if (url != null)
			url = url.replace("hotel?", "agent?");
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (ListHolder.getAgentList() == null)
			getActivity().getSupportLoaderManager().initLoader(200, null, this);
		else {
			LinearLayout layout = (LinearLayout) view
					.findViewById(R.id.linear_layout);
			layout.setVisibility(View.GONE);

			settingListAdapter(ListHolder.getAgentList());
		}
	}

	private void settingListAdapter(List<AgentGetter> list_data) {

		list.setVisibility(View.VISIBLE);
		adapter = new MyAgentAdapter(getActivity(), R.layout.list_item,
				list_data);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
	}

	@Override
	public Loader<List<AgentGetter>> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new AgentAsyncLoader(getActivity(), url);
	}

	private void showToast(String errorText) {
		LinearLayout linear_ = (LinearLayout) view
				.findViewById(R.id.text_visibility);
		TextView text_ = (TextView) view.findViewById(R.id.error_text);
		linear_.setVisibility(View.VISIBLE);
		text_.setText(errorText);
	}

	@Override
	public void onLoadFinished(Loader<List<AgentGetter>> arg0,
			List<AgentGetter> list_data) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.linear_layout);
		layout.setVisibility(View.GONE);

		AgentGetter statu = list_data.get(0);
		if (statu.status == 111) {
			ListHolder.setAgentList(list_data);
			settingListAdapter(list_data);
		} else if (statu.status == ViewHolder.data_not_found) {
			Log.d("AgentLoadStatus", ViewHolder.data_not_found + "data not found");
			showToast("Gosh! No Agent Found in this area");
		} else {
			Log.d("AgentLoadStatus", ViewHolder.no_internet + "no internet");
			showToast("Opps! No Internet Connection");
		}

	/*	LinearLayout text = (LinearLayout) view
				.findViewById(R.id.text_visibility);
		text.setVisibility(View.VISIBLE);

		ListHolder.setAgentList(list_data);
		settingListAdapter(list_data);*/

	}

	@Override
	public void onLoaderReset(Loader<List<AgentGetter>> arg0) {
		// TODO Auto-generated method stub

	}

	private class MyAgentAdapter extends ArrayAdapter<AgentGetter> {

		LayoutInflater inflater;
		Context context;
		List<AgentGetter> mItems;
		AgentGetter agentGetter;

		public MyAgentAdapter(Context context, int textViewResourceId,
				List<AgentGetter> agentItems) {
			super(context, textViewResourceId, agentItems);
			// TODO Auto-generated constructor stub
			this.context = context;
			this.mItems = agentItems;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.agent_list_item, null);

				holder = new ViewHolder();
				holder.txt_name = (TextView) convertView
						.findViewById(R.id.hotel_name);
				holder.txt_address = (TextView) convertView
						.findViewById(R.id.hotel_address);
				holder.txt_email = (TextView) convertView
						.findViewById(R.id.hotel_email);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			agentGetter = mItems.get(position);

			holder.txt_name.setText(agentGetter.name);
			holder.txt_address.setText(agentGetter.state);
			holder.txt_email.setText(agentGetter.type);
			return convertView;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), AgentDetail.class);
		intent.putExtra("agent", position);
		startActivity(intent);
	}

	@SuppressWarnings("rawtypes")
	private void destroyLoader() {
		Loader loader = getActivity().getSupportLoaderManager().getLoader(200);
		if (loader != null && loader.isStarted())
			loader.stopLoading();
		Log.d("Loader", "Loader Has Been Stoped");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// destroyLoader();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		destroyLoader();
	}
}
