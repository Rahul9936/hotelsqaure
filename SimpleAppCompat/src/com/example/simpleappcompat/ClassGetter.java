package com.example.simpleappcompat;

import java.io.Serializable;

public class ClassGetter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String address;
	String state;
	String phone;
	String fax;
	String email;
	String website;
	String type;
	String room;
	String city;
	int status;

	public ClassGetter(String name, String address, String state, String phone,String fax,
			String email, String website, String type, String room, String city, int status) {
		super();
		this.name = name;
		this.address = address;
		this.state = state;
		this.phone = phone;
		this.fax = fax;
		this.email = email;
		this.website = website;
		this.type = type;
		this.room = room;
		this.city = city;
		this.status = status;
	}

}
