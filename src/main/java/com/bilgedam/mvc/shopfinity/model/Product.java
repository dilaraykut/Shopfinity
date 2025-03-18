package com.bilgedam.mvc.shopfinity.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.bilgedam.mvc.shopfinity.enums.QuantityType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "urunler")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(min = 2, max = 50, message = "{product.name.size.error}")
	private String name;
	@Size(min = 2, max = 50, message = "{product.name.size.error}")
	private String enName;

	@ManyToOne
	private Category category;
	private String barcode;
	private double quantity;
	private QuantityType quantityType;
	private double criticalStockLevel;
	private String image;

	private String description;
	private String enDescription;

	private BigDecimal price;

	@Past
	private LocalDateTime insertedDate;
	private UserEntity insertedUser;
	private LocalDateTime lastUpdateDate;
	private UserEntity lastUpdateUser;

	@Version
	private Timestamp version;

	private boolean deleted;

}
