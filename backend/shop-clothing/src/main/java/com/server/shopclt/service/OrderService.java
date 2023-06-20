package com.server.shopclt.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.server.shopclt.payload.request.OrderRequest;
import com.server.shopclt.payload.response.OrderResponse;

public interface OrderService {
	
	public List<OrderResponse> getOrder();
	
	public ResponseEntity<?> getOrderbyUserIdAndOrderId(Long orderId);

	public ResponseEntity<?> addOrder(OrderRequest request);
	
	public List<OrderResponse> getALL();
	
	public ResponseEntity<?> getOrderbyId(Long orderId);
	
	public ResponseEntity<?> updateStatus(OrderRequest request, Long orderId);
}
