package com.cg.dealscoupan.userservice.jwt;

import com.cg.dealscoupan.userservice.entity.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
	private final String token;
	private final ClientType clientType;
	private final String id;
}
