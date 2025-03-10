package com.bilgedam.mvc.shopfinity.dto;

import java.sql.Timestamp;

import com.bilgedam.mvc.shopfinity.enums.QuantityType;
import com.bilgedam.mvc.shopfinity.model.Category;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	@Size(min=2,max=30, message = "{product.name.size.error}")
	private String name;
	@Size(min=2,max=30, message = "{product.name.size.error}")
	private String enName;
	@ManyToOne
	private Category category;
	private String barcode;
	private double quantity; // 3
	private QuantityType quantityType; // KG
	private double criticalStockLevel;
	private String image;

	private Timestamp version;
	
}
