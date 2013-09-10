package com.example.simpleappcompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AgentDetail extends ActionBarActivity implements OnClickListener{

	String tel_no, email_id;
	private void setViewOpt(){
		findViewById(R.id.map_layout).setVisibility(View.GONE);
		ImageView img = (ImageView) findViewById(R.id.agent_type);
		img.setImageResource(R.drawable.agenttype);
		setTitle("Agent");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.interactive);
		setViewOpt();
		int i = getIntent().getIntExtra("agent", 0);
		setData(i);
		
		(findViewById(R.id.inter_call)).setOnClickListener(this);
		(findViewById(R.id.inter_email)).setOnClickListener(this);
	}

	private void setData(int i) {
		AgentGetter agent = ListHolder.getAgentAtPosition(i);
		tel_no = agent.phone;
		email_id = agent.email;
		((TextView) findViewById(R.id.i_hotel_name)).setText(agent.name);
		((TextView) findViewById(R.id.i_hotel_address)).setText(agent.address);
		((TextView) findViewById(R.id.i_contact)).setText(agent.phone);
		((TextView) findViewById(R.id.i_email_id)).setText(agent.email);
		((TextView) findViewById(R.id.i_state)).setText(agent.state);
		((TextView) findViewById(R.id.i_star)).setText(agent.person);
		((TextView) findViewById(R.id.i_rooms)).setText(agent.type);
		((TextView) findViewById(R.id.i_fax)).setText(agent.fax);
		findViewById(R.id.view_table).setVisibility(View.GONE);
		findViewById(R.id.view_web).setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.inter_call:
			
			String url = "tel:" + tel_no;
			Intent call = new Intent(Intent.ACTION_CALL, Uri.parse(url));
			if(tel_no.length() > 6)
				startActivity(call);
			else
				makeOwnToast("Invalid Phone Number");	
			break;
		case R.id.inter_email :
			Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_EMAIL, new String[]{email_id});		  
			email.putExtra(Intent.EXTRA_SUBJECT, "subject");
			email.putExtra(Intent.EXTRA_TEXT, "Hello Buddy How are you");
			email.setType("message/rfc822");
			if(email_id.length() > 6)
				startActivity(Intent.createChooser(email, "Choose an Email client :"));
			else
				makeOwnToast("Invalid Email id");
			break;
		}
	}
	
	private void makeOwnToast(String msg){
		Toast.makeText(AgentDetail.this, msg, Toast.LENGTH_SHORT).show();
	}
}
