package com.server.shopclt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.server.shopclt.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	@Query("Select c from Category c where c.name =?1")
	Optional<Category> findCategorybyName(String name);

}
