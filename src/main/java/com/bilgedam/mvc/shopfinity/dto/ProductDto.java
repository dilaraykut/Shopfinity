package com.bilgedam.mvc.shopfinity.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.bilgedam.mvc.shopfinity.enums.QuantityType;
import com.bilgedam.mvc.shopfinity.model.Category;
import com.bilgedam.mvc.shopfinity.model.UserEntity;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	@Size(min = 2, max = 30, message = "{product.name.size.error}")
	private String name;
	@Size(min = 2, max = 30, message = "{product.name.size.error}")
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

	private boolean deleted;

	private Timestamp version;

}
