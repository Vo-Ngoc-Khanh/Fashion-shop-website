package com.server.shopclt.payload.response;

import com.server.shopclt.entity.User;

import lombok.Data;

@Data
public class UserResponse {

	private Long id;
	private String fullname;
	private String roleName;
	private String email;
	private String password;
	private String address;
	private String phone;
	private Boolean status;
    
	
	public UserResponse(User user) {
		super();
		this.id = user.getUserId();
		this.fullname = user.getFullname();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.address = user.getAddress();
		this.phone = user.getPhoneNumber();
		this.status = user.getStatus();
		this.roleName = user.getRole().getName();
	}
	
	
}
