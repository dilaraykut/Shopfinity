package com.bilgedam.mvc.shopfinity.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bilgedam.mvc.shopfinity.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	Page<Product> findByDeletedFalseAndNameLikeIgnoreCase(Pageable pageable, String string);

	Page<Product> findByDeletedFalseAndEnNameLikeIgnoreCase(Pageable pageable, String string);

	List<Product> findByCategoryIdAndDeletedFalse(Short categoryId);

	@Query("SELECT p FROM Product p WHERE p.id IN :ids")
	List<Product> getProductsByIds(List<Long> ids);
}
