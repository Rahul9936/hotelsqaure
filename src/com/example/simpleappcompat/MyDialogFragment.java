package com.example.simpleappcompat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyDialogFragment extends DialogFragment{

	static MyDialogFragment newInstance() {
		return new MyDialogFragment();
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		TextView text = new TextView(getActivity());
		text.setText("Dialog is runnnig");
		text.setTextSize(30);
		text.setGravity(Gravity.CENTER);
		text.setBackgroundColor(Color.GRAY);
		return text;
	}

}
