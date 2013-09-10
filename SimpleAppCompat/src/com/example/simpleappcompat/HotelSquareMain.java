package com.example.simpleappcompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class HotelSquareMain extends FragmentActivity implements TextWatcher,
		OnClickListener {

	String[] str = { "VARANASI", "LUCKNOW", "DELHI", "BANGLORE" };
	String tex;
	Button btn;
	AutoCompleteTextView auto;

	/*private void startAnimation() {
		if (ViewHolder.anim) {
			ImageView im = (ImageView) findViewById(R.id.imageView1);
			Animation animAccelerateDecelerate = AnimationUtils.loadAnimation(
					this, R.anim.start_anim);
			im.startAnimation(animAccelerateDecelerate);
			ViewHolder.anim = false;
		}
	}*/

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.inflate);
		auto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, str);
		auto.setAdapter(adapter);
		auto.addTextChangedListener(this);
		//startAnimation();
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

	@SuppressLint("DefaultLocale")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		hideKeyboard();
		if (v.getId() == R.id.btn_search) {
			Log.d("btn", "clicked");
		}
		if (tex != null) {
			Log.d("TEXT", tex);

			String url = "http://hotelsquare.azurewebsites.net/hotel?id="
					+ tex.toUpperCase();
			Intent intent = new Intent(HotelSquareMain.this,
					ShowSearchList.class);
			intent.putExtra("search", url);
			startActivity(intent);
		}
	}

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(auto.getWindowToken(), 0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		hideKeyboard();
		return super.onTouchEvent(event);
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
