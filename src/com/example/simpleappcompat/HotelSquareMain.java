package com.example.simpleappcompat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class HotelSquareMain extends FragmentActivity implements TextWatcher, OnClickListener{

	String[] str = { "VARANASI", "LUCKNOW", "DELHI", "BANGLORE" };
	String tex;
	Button btn;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.inflate);
		AutoCompleteTextView auto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, str);
		auto.setAdapter(adapter);
		auto.addTextChangedListener(this);
		
		btn = (Button) findViewById(R.id.btn_search);
		btn.setOnClickListener(this);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ListHolder.setAgentList(null);
		ListHolder.setList(null);
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
			Intent intent = new Intent(HotelSquareMain.this, ShowSearchList.class);
			intent.putExtra("search", url);
			startActivity(intent);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		tex = s.toString();
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
}
