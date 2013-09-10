package com.example.simpleappcompat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

	String url;
	public MyPagerAdapter(FragmentManager fm, String url) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.url = url;
	}

	@Override
	public Fragment getItem(int i) {
		// TODO Auto-generated method stub
		Fragment fragment = null; 
		switch(i){
		case 0:
			fragment = new HotelFragment();
			Bundle bndl = new Bundle();
			bndl.putString("url", url);
			fragment.setArguments(bndl);
			break;
		case 1:
			fragment = new AgentFragment();
			Bundle bndle = new Bundle();
			bndle.putString("url", url);
			fragment.setArguments(bndle);
			break;
		}
        return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
}
