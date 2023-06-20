package com.server.shopclt.service;

import org.springframework.http.ResponseEntity;

import com.server.shopclt.payload.request.LoginRequest;
import com.server.shopclt.payload.request.RegisterRequest;
import com.server.shopclt.payload.response.JwtResponse;
import com.server.shopclt.payload.response.UserResponse;

public interface AuthService {

	public ResponseEntity<?> register(RegisterRequest request);
	
	public JwtResponse login(LoginRequest request);
	
	public UserResponse profile();
}
