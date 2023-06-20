package com.server.shopclt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.shopclt.payload.request.LoginRequest;
import com.server.shopclt.payload.request.RegisterRequest;
import com.server.shopclt.payload.response.JwtResponse;
import com.server.shopclt.payload.response.UserResponse;
import com.server.shopclt.service.AuthService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request){
		return authService.register(request);
	}
	
	@PostMapping("/login")
	public JwtResponse login(@RequestBody LoginRequest request){
		return authService.login(request);
	}
	
	@GetMapping("/profile")
	public UserResponse profile(){
		return authService.profile();
	}
}
