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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.shopclt.payload.request.ProductRequest;
import com.server.shopclt.payload.response.ProductResponse;
import com.server.shopclt.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("")
	public List<ProductResponse> getAll(){
		return productService.getAll();
	}
	
	@GetMapping("/active")
	public List<ProductResponse> getAllProductActive(){
		return productService.getAllProductActive();
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> create(@RequestBody ProductRequest request) {
		return productService.create(request);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> update(@RequestBody ProductRequest request, @PathVariable Long id) {
		return productService.update(request, id);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return productService.delete(id);
	}
	
	@PutMapping("/status/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateStatus(@RequestBody ProductRequest request, @PathVariable Long id) {
		return productService.updateStatus(request, id);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getbyId(@PathVariable Long id){
		return productService.getbyId(id);
	}
	
	@GetMapping("/keyword")
	public List<ProductResponse> findbyKeyord(@RequestParam String keyword){
		return productService.search(keyword);
	}
	
	@GetMapping("/category")
	public List<ProductResponse> findbyCategory(@RequestParam String categoryName){
		return productService.findbyCategory(categoryName);
	}
}
