package com.server.shopclt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.server.shopclt.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query("Select u from User u where u.email =?1")
	User findbyEmail(String username);

}
