package com.server.shopclt.payload.request;

import lombok.Data;

@Data
public class OrderDetailRequest{
	private Long productId;
	private int quantity;
}
