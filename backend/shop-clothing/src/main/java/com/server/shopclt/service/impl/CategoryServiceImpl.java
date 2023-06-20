package com.server.shopclt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.shopclt.entity.Category;
import com.server.shopclt.payload.request.CategoryRequest;
import com.server.shopclt.payload.response.ApiResponse;
import com.server.shopclt.payload.response.CategoryResponse;
import com.server.shopclt.repository.CategoryRepository;
import com.server.shopclt.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	@Override
	public List<CategoryResponse> getAll() {
		
		List<Category> categories = categoryRepository.findAll();
		List<CategoryResponse> lst = new ArrayList<>();
		for(Category i:categories) {
			CategoryResponse c = new CategoryResponse(i);
			lst.add(c);
		}		
		return lst;		
	}
	@Override
	public ResponseEntity<?> create(CategoryRequest request) {	
		Optional<Category> check = categoryRepository.findCategorybyName(request.getCategoryName());
		if(check.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Category is already existing",400));	
		}
		Category category = new Category();
		category.setName(request.getCategoryName());
		categoryRepository.save(category);
		return ResponseEntity.status(HttpStatus.CREATED).body(new CategoryResponse(category));	
		
			
	}
	@Override
	public ResponseEntity<?> update(CategoryRequest request, Long Id) {
		Optional<Category> check = categoryRepository.findCategorybyName(request.getCategoryName());
		if(check.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Category is already existing",400));	
		}
		Optional<Category> category = categoryRepository.findById(Id);
		if(category.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category not found",404));	
		}
		
		category.get().setName(request.getCategoryName());
		categoryRepository.save(category.get());			
		return ResponseEntity.ok().body(new CategoryResponse(category.get()));
	}
	@Override
	public ResponseEntity<?> detele(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if(category.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category not found",404));
		}
		categoryRepository.delete(category.get());
		return ResponseEntity.ok().body(new ApiResponse("Delete successfully", 200));
	}
	@Override
	public ResponseEntity<?> getById(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if(category.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category not found",404));
		}
		return ResponseEntity.ok().body(new CategoryResponse(category.get()));
	}

}
