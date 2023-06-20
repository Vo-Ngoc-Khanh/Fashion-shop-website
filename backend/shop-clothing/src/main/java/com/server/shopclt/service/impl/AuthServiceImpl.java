package com.server.shopclt.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.server.shopclt.entity.Role;
import com.server.shopclt.entity.User;
import com.server.shopclt.exception.DataNotFoundException;
import com.server.shopclt.payload.request.LoginRequest;
import com.server.shopclt.payload.request.RegisterRequest;
import com.server.shopclt.payload.response.ApiResponse;
import com.server.shopclt.payload.response.JwtResponse;
import com.server.shopclt.payload.response.UserResponse;
import com.server.shopclt.repository.RoleRepository;
import com.server.shopclt.repository.UserRepository;
import com.server.shopclt.service.AuthService;
import com.server.shopclt.utils.JwtProviderUtils;
import com.server.shopclt.utils.SecurityUtils;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private JwtProviderUtils tokenProvider;

	@Override
	public ResponseEntity<?> register(RegisterRequest request) {

		String email = request.getEmail();
		User user = userRepository.findbyEmail(email);
		if (user == null) {
			User register = new User();
			register.setFullname(request.getFullname());
			register.setEmail(email);
			register.setPassword(passwordEncoder.encode(request.getPassword()));
			Optional<Role> role = roleRepository.findById((long) 2);
			register.setRole(role.get());
			register.setStatus(Boolean.TRUE);
			userRepository.save(register);
			return ResponseEntity.ok(new ApiResponse("Register successfully.",201));
		}
		return ResponseEntity.badRequest().body(new ApiResponse("Email already register!!!",400));
	}

	@Override
	public JwtResponse login(LoginRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));		
		SecurityContextHolder.getContext().setAuthentication(authentication);	
		String jwt = tokenProvider.generateTokenUsingUserName(request.getEmail());

		return new JwtResponse(jwt);
	}

	@Override
	public UserResponse profile() {
        User user=userRepository.findbyEmail(SecurityUtils.getEmail());
        if(Objects.isNull(user)){        	
            throw  new DataNotFoundException("Can't find this account");
        }
        return new UserResponse(user);
	}

}
