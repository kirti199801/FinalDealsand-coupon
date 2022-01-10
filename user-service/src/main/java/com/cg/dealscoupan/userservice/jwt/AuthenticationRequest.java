package com.cg.dealscoupan.userservice.jwt;

import com.cg.dealscoupan.userservice.entity.ClientType;
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
