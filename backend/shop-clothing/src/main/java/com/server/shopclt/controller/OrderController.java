package com.server.shopclt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.shopclt.payload.request.OrderRequest;
import com.server.shopclt.payload.response.OrderResponse;
import com.server.shopclt.service.OrderService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/user")
	public List<OrderResponse> getOrder(){
		return orderService.getOrder();
	}
	
	@GetMapping("/user/{orderId}")
	public ResponseEntity<?> getOrderByUserIdAndOrderId(@PathVariable Long orderId){
		return orderService.getOrderbyUserIdAndOrderId(orderId);
	}
	
	
	@PostMapping("")
	public ResponseEntity<?> addOrder(@RequestBody OrderRequest request){
		return orderService.addOrder(request);
	}
	
	@GetMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<OrderResponse> getAll(){
		return orderService.getALL();
	}
	
	@GetMapping("/{orderId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getOrderById(@PathVariable Long orderId){
		return orderService.getOrderbyId(orderId);
	}
	
	@PutMapping("status/{orderId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateStatus(@RequestBody OrderRequest request,@PathVariable Long orderId){
		return orderService.updateStatus(request, orderId);
	}

}
