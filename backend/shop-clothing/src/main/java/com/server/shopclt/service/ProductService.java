package com.server.shopclt.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.server.shopclt.payload.request.ProductRequest;
import com.server.shopclt.payload.response.ProductResponse;

public interface ProductService {

	public List<ProductResponse> getAll();
	
	public ResponseEntity<?> getbyId(Long id);
	
	public ResponseEntity<?> create(ProductRequest request);
	
	public ResponseEntity<?> update(ProductRequest request, Long id);
	
	public ResponseEntity<?> updateStatus(ProductRequest request, Long id);
	
	public ResponseEntity<?> delete(Long id);
	
	public List<ProductResponse>findbyCategory(String categoryName);
	
	public List<ProductResponse> search(String keyword);
	
	public List<ProductResponse> getAllProductActive();
}
