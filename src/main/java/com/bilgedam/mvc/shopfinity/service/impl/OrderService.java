package com.bilgedam.mvc.shopfinity.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bilgedam.mvc.shopfinity.model.Order;
import com.bilgedam.mvc.shopfinity.model.Product;
import com.bilgedam.mvc.shopfinity.model.UserEntity;
import com.bilgedam.mvc.shopfinity.repository.OrderRepository;
import com.bilgedam.mvc.shopfinity.service.OrderReadable;
import com.bilgedam.mvc.shopfinity.service.OrderWriteable;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderReadable, OrderWriteable {

	private final OrderRepository orderRepository;
	private final ModelMapper modelMapper;

	@Override
	public List<Order> getList() {

		return orderRepository.findAll();
	}

	@Override
	public Order getById(Long id) {

		return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}

	@Override
	public void add(Order order) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserEntity user = (UserEntity) authentication.getPrincipal();

		order.setInsertedUser(user);

		orderRepository.save(order);

	}

	@Override
	public void change(Long id, Order updatedOrder) {
		Order existingOrder = orderRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Sipariş bulunamadı"));

		existingOrder.setProducts(updatedOrder.getProducts());

		orderRepository.save(existingOrder);
	}

	@Override
	public void remove(Long id) {
		Order orderToDelete = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		orderToDelete.setDeleted(true);
		orderRepository.save(orderToDelete);

	}

	@Override
	public List<Order> getList(String sortBy, String direction) {

		return null;
	}

	@Override
	public List<Order> getByInsertedUserEmail(String email) {

		return orderRepository.findOrdersByUserEmail(email);
	}
}
