package com.cg.dealscoupan.userservice.entity;

import com.cg.dealscoupan.userservice.client.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

	//Attributes
	private String id;
	private String name;
	private String email;
	private String password;

	private List<Coupon> coupons;

	//Parameterized constructor
	public Company(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
}
