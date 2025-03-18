package com.bilgedam.mvc.shopfinity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bilgedam.mvc.shopfinity.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o WHERE o.insertedUser.email = :email")
	List<Order> findOrdersByUserEmail(@Param("email") String email);

}
