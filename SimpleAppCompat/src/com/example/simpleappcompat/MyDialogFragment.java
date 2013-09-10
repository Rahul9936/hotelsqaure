package com.example.simpleappcompat;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class MyDialogFragment extends DialogFragment {

	@SuppressWarnings("unused")
	private Dialog mDialog;

	static MyDialogFragment newInstance() {
		return new MyDialogFragment();
	}

	public MyDialogFragment() {
		super();
		// TODO Auto-generated constructor stub
		mDialog = null;
	}

	public void setDialog(Dialog dialog) {
		mDialog = dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		TextView text = new TextView(getActivity());
		text.setText("Wait... fetching hotel Location");
		text.setTextSize(20);
		text.setGravity(Gravity.CENTER);
		text.setBackgroundColor(Color.GRAY);
		return text;
	}

}
