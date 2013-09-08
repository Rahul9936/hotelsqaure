package com.example.simpleappcompat;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LoadData extends AsyncTask<String, Void, JSONObject>{
	
	TextView txt ;
	Context host;
	ListView list;
	public LoadData(FragmentActivity host) {
		// TODO Auto-generated constructor stub
		this.host = host;
		list = (ListView) host.findViewById(R.id.listview);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(host,
				android.R.layout.simple_list_item_1, new String[] { "ad",
						"dsv", "dsv" });
		list.setAdapter(adapter);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		//
		Log.d("TAG", "in oPreExecute method");
	}
	
	@Override
	protected JSONObject doInBackground(String... params) {
		// TODO Auto-generated method stub
		Log.d("TAG", "in doinBackground method");
		JSONParser jParser = new JSONParser();
		//JSONObject json = jParser.getJSONFromUrl(params[0]);
		return null;
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result != null)
			txt.setText(result.toString());
		
		Log.d("TAG2", txt.getText().toString());
	}	
}
