package com.server.shopclt.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.server.shopclt.payload.request.CategoryRequest;
import com.server.shopclt.payload.response.CategoryResponse;

public interface CategoryService {

	public List<CategoryResponse> getAll();
	
	public ResponseEntity<?> getById(Long id);
	
	public ResponseEntity<?> create(CategoryRequest request);
	
	public ResponseEntity<?> update(CategoryRequest request, Long id);
	
	public ResponseEntity<?> detele(Long id);
}
