package com.server.shopclt.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.shopclt.entity.Order;
import com.server.shopclt.entity.OrderDetail;
import com.server.shopclt.entity.Product;
import com.server.shopclt.entity.User;
import com.server.shopclt.enums.StatusOrder;
import com.server.shopclt.payload.request.OrderDetailRequest;
import com.server.shopclt.payload.request.OrderRequest;
import com.server.shopclt.payload.response.ApiResponse;
import com.server.shopclt.payload.response.OrderResponse;
import com.server.shopclt.repository.OrderDetailRepository;
import com.server.shopclt.repository.OrderRepository;
import com.server.shopclt.repository.ProductRepository;
import com.server.shopclt.repository.UserRepository;
import com.server.shopclt.service.OrderService;
import com.server.shopclt.utils.SecurityUtils;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Override
	public ResponseEntity<?> addOrder(OrderRequest request) {
		try {
			Order order = new Order();
			
			order.setCreateAt(LocalDate.now());
		    order.setShippingAddress(request.getAddress());
		    order.setTotalAmount(request.getTotalAmount());
		    order.setStatus(StatusOrder.Pending);
		    
		    User user = userRepository.findbyEmail(SecurityUtils.getEmail());
		    order.setUser(user);
		    orderRepository.save(order);
		    
		    user.setAddress(request.getAddress());
		    user.setPhoneNumber(request.getPhone());
		    userRepository.save(user);
		    
		    List<OrderDetailRequest> lst = request.getOrderDetail();
		    for(OrderDetailRequest i:lst) {
		    	OrderDetail detail = new OrderDetail();
		    	detail.setOrder(order);	    			    	    	
		    	detail.setQuantity(i.getQuantity());		    	
		    	Optional<Product> product = productRepository.findById(i.getProductId());
		    	detail.setProduct(product.get());	    	
		    	orderDetailRepository.save(detail);
		    	
		    }
		    
		    return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Order creted successfully", 201));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), 400));
		}

	}

	@Override
	public List<OrderResponse> getOrder() {
		User user = userRepository.findbyEmail(SecurityUtils.getEmail());
		List<Order> lstOrders = orderRepository.findByUserId(user.getUserId());
		List<OrderResponse> lstOrderResponses = new ArrayList<>();
		for(Order i:lstOrders) {
			lstOrderResponses.add(new OrderResponse(i));
		}
		return lstOrderResponses;
	}

	@Override
	public ResponseEntity<?> getOrderbyUserIdAndOrderId(Long orderId) {
		User user = userRepository.findbyEmail(SecurityUtils.getEmail());
		Order order = orderRepository.findByUserIdandAndOrderId(user.getUserId(),orderId);
		if(order != null) {
			return ResponseEntity.ok(new OrderResponse(order));		
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Order not found", 404));
	}

	@Override
	public List<OrderResponse> getALL() {
		
		List<Order> lstOrders = orderRepository.findAll();
		List<OrderResponse> lstOrderResponses = new ArrayList<>();
		for(Order i:lstOrders) {
			lstOrderResponses.add(new OrderResponse(i));
		}
		return lstOrderResponses;
	}

	@Override
	public ResponseEntity<?> getOrderbyId(Long orderId) {
		Optional<Order> order = orderRepository.findById(orderId);
		if(order.get() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Order not found", 404));	
		}
		return ResponseEntity.ok(new OrderResponse(order.get()));	
	}

	@Override
	public ResponseEntity<?> updateStatus(OrderRequest request,Long orderId) {
		Optional<Order> order = orderRepository.findById(orderId);
		if(order.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Order not found",404));
		}
		if(request.getCancelReason()!= null) {
			order.get().setCancellationReason(request.getCancelReason());
		}
		if(request.getEddate()!= null) {
			order.get().setExpectedDeliveryDate(request.getEddate());
		}
		order.get().setStatus(request.getStatus());
		orderRepository.save(order.get());
		return ResponseEntity.ok().body(new OrderResponse(order.get()));
	}

}
