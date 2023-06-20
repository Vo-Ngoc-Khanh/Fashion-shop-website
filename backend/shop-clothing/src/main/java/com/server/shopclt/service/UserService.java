package com.server.shopclt.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.server.shopclt.payload.response.UserResponse;

public interface UserService {

	public List<UserResponse> getAll();
	
	public ResponseEntity<?> lock();
}
