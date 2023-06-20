package com.server.shopclt.payload.request;

import java.math.BigDecimal;

import com.server.shopclt.enums.Status;

import lombok.Data;

@Data
public class ProductRequest {
	
	private String name;
	private Long categoryId;
	private String image;
	private String description;
	private BigDecimal price;
    private int quantity;
    private Status status; 
}
