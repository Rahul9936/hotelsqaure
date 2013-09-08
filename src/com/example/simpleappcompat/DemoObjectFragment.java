package com.example.simpleappcompat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class DemoObjectFragment extends Fragment {//implements TextWatcher, OnClickListener{

/*	String[] str = { "VARANASI", "LUCKNOW", "DELHI", "BANGLORE" };
	public static final String ARG_OBJECT = "object";
	String tex;
	Button btn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.inflate, container, false);
		AutoCompleteTextView auto = (AutoCompleteTextView) rootView
				.findViewById(R.id.autoCompleteTextView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, str);
		auto.setAdapter(adapter);
		auto.addTextChangedListener(this);
		
		btn = (Button) rootView.findViewById(R.id.btn_search);
		btn.setOnClickListener(this);
		return rootView;
	}
	

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		tex = s.toString();
		Log.d("TEXT", s.toString());
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.btn_search){
			Log.d("btn", "clicked");
		}
		if(tex != null){
			Log.d("TEXT", tex);
			String url = "http://hotelsquare.azurewebsites.net/hotel?id=" + tex.toUpperCase();
			Intent intent = new Intent(getActivity(), ShowSearchList.class);
			intent.putExtra("search", url);
			startActivity(intent);
		}
	}*/
}
