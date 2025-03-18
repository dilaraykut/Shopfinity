package com.bilgedam.mvc.shopfinity.service.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bilgedam.mvc.shopfinity.dto.ListProperties;
import com.bilgedam.mvc.shopfinity.model.Category;
import com.bilgedam.mvc.shopfinity.repository.CategoryRepository;
import com.bilgedam.mvc.shopfinity.service.CategoryReadable;
import com.bilgedam.mvc.shopfinity.service.CategoryWriteable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryReadable, CategoryWriteable {

	private final CategoryRepository categoryRepository;

	@Override
	public void add(Category category) {

		categoryRepository.save(category);
	}

	@Override
	public void change(Short id, Category category) {

		Category categoryToUpdate = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());

		categoryToUpdate.setName(category.getName());
		categoryToUpdate.setEnName(category.getEnName());

		categoryRepository.save(categoryToUpdate);
	}

	@Override
	public void remove(Short id) {
		Category categoryToDelete = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());

		categoryToDelete.setDeleted(true);

		categoryRepository.save(categoryToDelete);
	}

	@Override
	public List<Category> getList(String sortBy, String direction) {
		Sort sort = null;

		if ("asc".equalsIgnoreCase(direction)) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}

		return categoryRepository.findByDeletedFalse(sort);

	}

	@Override
	public Category getById(Short id) {
		return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
	}

	@Override
	public List<Category> getList() {

		return categoryRepository.findByDeletedFalse();
	}

	@Override
	public Page<Category> getPaginatedAndSortedCategories(String sortBy, String direction, int page, int size) {
		Sort sort = "asc".equalsIgnoreCase(direction) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return categoryRepository.findByDeletedFalse(pageable);
	}

	@Override
	public Page<Category> getPaginatedAndSortedCategories(ListProperties listProperties) {
		Sort sort = "asc".equalsIgnoreCase(listProperties.getDirection())
				? Sort.by(listProperties.getSortBy()).ascending()
				: Sort.by(listProperties.getSortBy()).descending();

		Pageable pageable = PageRequest.of(listProperties.getPage(), listProperties.getSize(), sort);

		Locale locale = LocaleContextHolder.getLocale();

		if (locale.getLanguage().equals("tr"))
			return categoryRepository.findByDeletedFalseAndNameLikeIgnoreCase(pageable,
					"%" + listProperties.getKeyword() + "%");
		else if (locale.getLanguage().equals("en"))
			return categoryRepository.findByDeletedFalseAndEnNameLikeIgnoreCase(pageable,
					"%" + listProperties.getKeyword() + "%");
		else
			return categoryRepository.findByDeletedFalseAndEnNameLikeIgnoreCase(pageable,
					"%" + listProperties.getKeyword() + "%");

	}

}
