package com.bilgedam.mvc.shopfinity.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bilgedam.mvc.shopfinity.dto.ListProperties;
import com.bilgedam.mvc.shopfinity.model.Product;

public interface ProductReadable extends Readable<Product, Integer> {

	Page<Product> getPaginatedAndSortedProducts(ListProperties listProperties);

	List<Product> getProductsByCategoryId(Short categoryId);

}
