package com.cg.dealscoupan.userservice.entity;

import com.cg.dealscoupan.userservice.client.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Customer {

	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private List<Coupon> coupons = new ArrayList<>();

	//Parameterized constructor
	public Customer(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	//Default constructor
	public Customer() {
	}
}
