package com.bilgedam.mvc.shopfinity.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.bilgedam.mvc.shopfinity.model.Product;
import com.bilgedam.mvc.shopfinity.model.UserEntity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private UserEntity insertedUser;

	@ManyToMany
	@JoinTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> products;

	private int quantity;

	private Double totalPrice;

	private Double price;

	private LocalDateTime purchaseDate;

	@Version
	private Timestamp version;

	private boolean deleted;
}
