package com.server.shopclt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.shopclt.payload.request.CategoryRequest;
import com.server.shopclt.payload.response.CategoryResponse;
import com.server.shopclt.service.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("")
	public ResponseEntity<?> getAll(){
		List<CategoryResponse> lst = categoryService.getAll();
		return ResponseEntity.ok(lst);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getById(@PathVariable Long id){
		return categoryService.getById(id);
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> create(@RequestBody CategoryRequest request ){
		return categoryService.create(request);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> update(@RequestBody CategoryRequest request, @PathVariable Long id ){
		return categoryService.update(request,id);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id){
		return categoryService.detele(id);
	}
	
}
