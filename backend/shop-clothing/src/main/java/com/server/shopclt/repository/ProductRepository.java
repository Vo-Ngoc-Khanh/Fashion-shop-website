package com.server.shopclt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.shopclt.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query("select p from Product p, Category c where p.category.categoryId = c.categoryId and c.name =?1 and p.status = 'ACTIVE'")
	List<Product> findbyCategoryName(String categoryName);
	
	@Query("SELECT p FROM Product p WHERE p.name ILIKE %:keyword% and p.status = 'ACTIVE'")
    List<Product> findByKeyword(@Param("keyword") String keyword);

	@Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE'")
    List<Product> findAllProductActive();

}
