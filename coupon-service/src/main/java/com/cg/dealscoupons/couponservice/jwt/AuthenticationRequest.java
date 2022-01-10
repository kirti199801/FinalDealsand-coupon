package com.cg.dealscoupons.couponservice.jwt;

import com.cg.dealscoupons.couponservice.entity.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class AuthenticationRequest {

	private String email;
	private String password;
	private ClientType clientType;
	public AuthenticationRequest() {

	}

	public AuthenticationRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

}
