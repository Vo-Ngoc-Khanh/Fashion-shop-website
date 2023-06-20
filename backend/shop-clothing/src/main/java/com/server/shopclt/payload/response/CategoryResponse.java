package com.server.shopclt.payload.response;

import com.server.shopclt.entity.Category;

import lombok.Data;

@Data
public class CategoryResponse {

	private Long categoryId;
	private String categoryName;
	
	public CategoryResponse(Category category) {
		this.categoryId = category.getCategoryId();
		this.categoryName = category.getName();
	}
}
