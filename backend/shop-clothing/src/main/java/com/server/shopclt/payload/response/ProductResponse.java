package com.server.shopclt.payload.response;

import java.math.BigDecimal;

import com.server.shopclt.entity.Product;

import lombok.Data;

@Data
public class ProductResponse {
	
	private Long productId;
	private String name;
	private CategoryResponse category;
	private String image;
	private String description;
	private BigDecimal price;
    private int quantity;
    private String status;
	public ProductResponse(Product product) {
		super();
		this.productId = product.getProductId();
		this.name = product.getName();
		this.category = new CategoryResponse(product.getCategory());
		this.image = product.getImage();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.quantity = product.getQuantity();
		if (product.getStatus() != null) {
		    this.status = product.getStatus().name();
		}
	}
	
    

}
