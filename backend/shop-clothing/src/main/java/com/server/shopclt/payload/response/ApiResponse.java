package com.server.shopclt.payload.response;

import lombok.Data;

@Data
public class ApiResponse {

	private Object message;
	private int status;
	
	public ApiResponse(Object message, int status) {
		super();
		this.message = message;
		this.status = status;
	}
	
	
}
