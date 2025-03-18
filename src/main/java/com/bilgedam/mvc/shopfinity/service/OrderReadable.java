package com.bilgedam.mvc.shopfinity.service;

import java.util.List;

import com.bilgedam.mvc.shopfinity.model.Order;

public interface OrderReadable extends Readable<Order, Long> {

	List<Order> getList(String sortBy, String direction);

	List<Order> getByInsertedUserEmail(String email);
}
