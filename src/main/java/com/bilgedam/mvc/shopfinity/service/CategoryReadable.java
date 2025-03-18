package com.bilgedam.mvc.shopfinity.service;

import org.springframework.data.domain.Page;

import com.bilgedam.mvc.shopfinity.dto.ListProperties;
import com.bilgedam.mvc.shopfinity.model.Category;

public interface CategoryReadable extends Readable<Category, Short> {
	Page<Category> getPaginatedAndSortedCategories(ListProperties listProperties);

	Page<Category> getPaginatedAndSortedCategories(String sortBy, String direction, int page, int size);
}
