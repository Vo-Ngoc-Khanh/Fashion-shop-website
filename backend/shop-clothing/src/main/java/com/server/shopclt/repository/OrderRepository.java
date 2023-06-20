package com.server.shopclt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.server.shopclt.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query("Select o from Order o where o.user.userId =?1")
	List<Order> findByUserId(Long userId);

	@Query("Select o from Order o where o.user.userId =?1 and o.orderId=?2")
	Order findByUserIdandAndOrderId(Long userId, Long orderId);

}
