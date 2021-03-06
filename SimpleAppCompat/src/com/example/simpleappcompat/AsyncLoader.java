package com.example.simpleappcompat;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class AsyncLoader extends AsyncTaskLoader<List<ClassGetter>> {
	private String url;
	Context context;
	List<ClassGetter> myStringSet;
	private List<ClassGetter> mData = null;

	public AsyncLoader(Context context, String url) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.url = url;
		myStringSet = new ArrayList<ClassGetter>();
		Log.d("URL", url);
	}

	@Override
	public List<ClassGetter> loadInBackground() {
		// TODO Auto-generated method stub

		Log.d("Loader", "IN loding backg");
		JSONParser jParser = new JSONParser();
		JSONArray json = null;
		try {
			json = jParser.getJSONFromUrl(url);
			if(! returnErrorJson(json))
				parseName(json);
		} catch (Exception e) {
			Log.d("JSON ERROR", "No Internet in Hotel Loader");
			return null;
		}

		return myStringSet;
	}

	private boolean returnErrorJson(JSONArray json) {
		try {
			JSONObject obj = json.getJSONObject(0);
			int status = obj.getInt("status");
			ClassGetter getter = new ClassGetter(null, null, null, null, null,
					null, null, null, null, null, status);
			myStringSet.add(getter);
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private void parseName(JSONArray json) {
		String name, address, state, phone, fax, email, website, type, room, city;
		name = address = state = phone = fax = email = website = type = room = city = null;

		for (int i = 0; i < json.length(); i++) {
			try {
				JSONObject j_obj = json.getJSONObject(i);
				name = j_obj.getString("name");
				address = j_obj.getString("address");
				state = j_obj.getString("state");
				phone = j_obj.getString("phone");
				fax = j_obj.getString("fax");
				email = j_obj.getString("email");
				website = j_obj.getString("website");
				type = j_obj.getString("type");
				room = j_obj.getString("room");
				city = j_obj.getString("city");

				ClassGetter getter = new ClassGetter(name, address, state,
						phone, fax, email, website, type, room, city, 111);
				myStringSet.add(getter);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				return;
			} finally {
				// System.out.println(name);
			}

		}
	}

	@Override
	public void deliverResult(List<ClassGetter> data) {
		// TODO Auto-generated method stub
		Log.d("DELIVER RESULT", "DELIVER RESULT");
		if (isReset()) {
			if (data != null) {
				Log.d("DELIVER RESULT", "releasing resources");
			}
		}

		List<ClassGetter> oldData = data;
		mData = oldData;

		if (isStarted())
			super.deliverResult(data);

		if (oldData != null) {
			Log.d("DELIVER RESULT", "releasing resources when oldData is null");
		}

	}

	@Override
	protected void onStartLoading() {
		// TODO Auto-generated method stub
		Log.d("ONSTArt LOADING", "ONSTArt LOADING");
		if (mData != null) {
			Log.d("mData", "Not null");
			deliverResult(mData);
		} else {
			Log.d("ONSTArt LOADING", "force load");
			forceLoad();
		}
	}

	@Override
	protected void onStopLoading() {
		// TODO Auto-generated method stub
		cancelLoad();
	}

	@Override
	public void onCanceled(List<ClassGetter> data) {
		// TODO Auto-generated method stub
		super.onCanceled(data);
		Log.d("ON CANCELED", "canceled");
	}

	@Override
	protected void onReset() {
		// TODO Auto-generated method stub
		super.onReset();
		onStopLoading();
		if (mData != null)
			mData = null;
	}
}
