package com.bilgedam.mvc.shopfinity.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bilgedam.mvc.shopfinity.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Short>{

	List<Category> findByDeletedFalse();

	List<Category> findByDeletedFalse(Sort sort);

	Page<Category> findByDeletedFalse(Pageable pageable);

	Page<Category> findByDeletedFalseAndNameLike(Pageable pageable, String keyword);

	Page<Category> findByDeletedFalseAndNameLikeIgnoreCase(Pageable pageable, String string);
	Page<Category> findByDeletedFalseAndEnNameLikeIgnoreCase(Pageable pageable, String string);
}
