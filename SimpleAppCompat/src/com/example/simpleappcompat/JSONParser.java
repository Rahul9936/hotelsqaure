package com.example.simpleappcompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	JSONArray jsonArray;

	// constructor
	public JSONParser() {

	}

	public JSONArray getJSONFromUrl(String url) {

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpPost = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
			return returnJsonArray(ViewHolder.no_internet);
		}

		// try parse the string to a JSON object
		try {
			jsonArray = new JSONArray(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
			return returnJsonArray(ViewHolder.data_not_found);
		}
		return jsonArray;
	}

	private JSONArray returnJsonArray(int errorCode) {
		try {
			JSONArray array = new JSONArray();
			JSONObject json_object = new JSONObject();
			json_object.put("status", errorCode);
			array.put(json_object);
			return array;
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	}
}
