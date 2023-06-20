package com.server.shopclt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.shopclt.entity.Category;
import com.server.shopclt.entity.Product;
import com.server.shopclt.payload.request.ProductRequest;
import com.server.shopclt.payload.response.ApiResponse;
import com.server.shopclt.payload.response.ProductResponse;
import com.server.shopclt.repository.CategoryRepository;
import com.server.shopclt.repository.ProductRepository;
import com.server.shopclt.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<ProductResponse> getAll() {
		
		List<Product> products = productRepository.findAll();
		List<ProductResponse> lst = new ArrayList<>();
		for(Product i:products) {
			lst.add(new ProductResponse(i));
		}
		return lst;
	}

	@Override
	public ResponseEntity<?> create(ProductRequest request) {
		Product product = new Product();
		product.setName(request.getName());
		product.setImage("../assets/pages/images/product/fashion/"+request.getImage());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setQuantity(request.getQuantity());
		product.setStatus(request.getStatus());
		
		Optional<Category> category = categoryRepository.findById(request.getCategoryId());
		if(category.isPresent()) {
			product.setCategory(category.get());
			productRepository.save(product);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponse(product));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category not found",404));
	}

	@Override
	public ResponseEntity<?> update(ProductRequest request, Long id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found",404));
		}
		product.get().setName(request.getName());
		product.get().setImage(request.getImage());
		product.get().setDescription(request.getDescription());
		product.get().setPrice(request.getPrice());
		product.get().setQuantity(request.getQuantity());
		product.get().setStatus(request.getStatus());
		if(request.getCategoryId()!= null) {
			Optional<Category> category = categoryRepository.findById(request.getCategoryId());
			product.get().setCategory(category.get());
		}	
		productRepository.save(product.get());
		return ResponseEntity.ok().body(new ProductResponse(product.get()));
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found",404));
		}
		productRepository.delete(product.get());
		return ResponseEntity.ok().body(new ApiResponse("Delete successfully", 200));
	}

	@Override
	public ResponseEntity<?> updateStatus(ProductRequest request, Long id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found",404));
		}
		product.get().setStatus(request.getStatus());
		productRepository.save(product.get());
		return ResponseEntity.ok().body(new ProductResponse(product.get()));
	}

	@Override
	public ResponseEntity<?> getbyId(Long id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found",404));
		}
		return ResponseEntity.ok().body(new ProductResponse(product.get()));
	}

	@Override
	public List<ProductResponse> findbyCategory(String categoryName) {
		
		List<Product> products = productRepository.findbyCategoryName(categoryName);
		List<ProductResponse> lst = new ArrayList<>();
		for(Product i:products) {
			lst.add(new ProductResponse(i));
		}
		return lst;
	}

	@Override
	public List<ProductResponse> search(String keyword) {
		List<Product> products = productRepository.findByKeyword(keyword);
		List<ProductResponse> lst = new ArrayList<>();
		for(Product i:products) {
			lst.add(new ProductResponse(i));
		}
		return lst;
	}

	@Override
	public List<ProductResponse> getAllProductActive() {
		List<Product> products = productRepository.findAllProductActive();
		List<ProductResponse> lst = new ArrayList<>();
		for(Product i:products) {
			lst.add(new ProductResponse(i));
		}
		return lst;
	}
}
