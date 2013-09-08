package com.example.simpleappcompat;

import java.util.List;

import android.app.Activity;
import android.content.Context;
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

public class AgentFragment extends Fragment implements LoaderCallbacks<List<AgentGetter>>,
		OnItemClickListener{
	
	ListView list;
	String url;
	View view;
	MyAgentAdapter adapter;
	List<AgentGetter> list_collection;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		url = getArguments().getString("url");
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
		url = url.replace("hotel?", "agent?");
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if(ListHolder.getAgentList() == null)
			getActivity().getSupportLoaderManager().initLoader(200, null, this);
		else{
			LinearLayout layout = (LinearLayout) view.findViewById(R.id.linear_layout);
			layout.setVisibility(View.GONE);
			list.setVisibility(View.VISIBLE);
			ListHolder.setAgentList(ListHolder.getAgentList());
			adapter = new MyAgentAdapter(getActivity(),
					R.layout.list_item, ListHolder.getAgentList());
			list.setAdapter(adapter);
			//adapter.notifyDataSetChanged();
			list.setOnItemClickListener(this);
		}
	}

	@Override
	public Loader<List<AgentGetter>> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new AgentAsyncLoader(getActivity(), url);
	}

	@Override
	public void onLoadFinished(Loader<List<AgentGetter>> arg0,
			List<AgentGetter> list_data) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.linear_layout);
		layout.setVisibility(View.GONE);
		if(list_data == null){
		
			TextView text = (TextView) view.findViewById(R.id.list_visibility);
			text.setVisibility(View.VISIBLE);
		}
		else{
			
			list.setVisibility(View.VISIBLE);
			ListHolder.setAgentList(list_data);
			adapter = new MyAgentAdapter(getActivity(),
					R.layout.list_item, list_data);
			list.setAdapter(adapter);
			//adapter.notifyDataSetChanged();
			list.setOnItemClickListener(this);
		}
	}

	@Override
	public void onLoaderReset(Loader<List<AgentGetter>> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private class MyAgentAdapter extends ArrayAdapter<AgentGetter>{

		LayoutInflater inflater;
		Context context;
		List<AgentGetter> mItems;
		AgentGetter agentGetter;
		TextView txt_name, txt_address, txt_email;
		
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
			View v = convertView;
			if(v == null){
				inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.agent_list_item, null);
			}
				agentGetter = mItems.get(position);
				txt_name = (TextView) v.findViewById(R.id.hotel_name);
				txt_address = (TextView) v.findViewById(R.id.hotel_address);
				txt_email = (TextView) v.findViewById(R.id.hotel_email);
				
				txt_name.setText(agentGetter.name);
				txt_address.setText(agentGetter.state);
				txt_email.setText(agentGetter.type);
				return v;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("rawtypes")
	private void destroyLoader(){
		Loader loader = getActivity().getSupportLoaderManager().getLoader(200);
		if(loader != null && loader.isStarted())
			loader.stopLoading();
		Log.d("Loader", "Loader Has Been Stoped");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		destroyLoader();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		destroyLoader();
	}
}
