package com.example.simpleappcompat;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

public class ShowSearchList extends ActionBarActivity implements
		ActionBar.TabListener, OnPageChangeListener {

	MyPagerAdapter pagerAdapter;
	ViewPager viewPager;
	ListView list;
	String s;

	private void specifyActionBar() {
		final String[] tabs = { "Hotels", "Agents" };
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (int i = 0; i < 2; i++) {
			Tab tab = actionBar.newTab();
			tab.setText(tabs[i]);
			tab.setTabListener(this);
			actionBar.addTab(tab);
		}
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		s = getIntent().getStringExtra("search");
		list = (ListView) this.findViewById(R.id.listview);
		String url_id = getIntent().getStringExtra("search");
		pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), url_id);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(this);
		specifyActionBar();
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		getSupportActionBar().setSelectedNavigationItem(position);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}
}
