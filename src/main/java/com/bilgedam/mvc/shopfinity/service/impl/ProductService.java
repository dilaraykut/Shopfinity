package com.bilgedam.mvc.shopfinity.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.modelmapper.ModelMapper;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bilgedam.mvc.shopfinity.dto.ListProperties;
import com.bilgedam.mvc.shopfinity.model.Product;
import com.bilgedam.mvc.shopfinity.model.UserEntity;
import com.bilgedam.mvc.shopfinity.repository.ProductRepository;
import com.bilgedam.mvc.shopfinity.service.ProductReadable;
import com.bilgedam.mvc.shopfinity.service.ProductWriteable;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductReadable, ProductWriteable{

	//@Autowired
	private final ProductRepository productRepository;
	
	private final ModelMapper modelMapper;
	
	
	@Override
	public List<Product> getList() {
		
		return productRepository.findAll();
	}

	@Override
	public Product getById(Integer id) {
		
		return productRepository.findById(id).orElseThrow(()-> new EntityNotFoundException());
	}

	@Override
	public List<Product> getList(String sortBy, String direction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Product entity) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		UserEntity user = (UserEntity)authentication.getPrincipal();
		
		entity.setInsertedUser(user);
		
		productRepository.save(entity);
		
	}

	@Override
	public void change(Integer id, Product entity) {
		Product productToUpdate = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
		
		if(!productToUpdate.getVersion().equals(entity.getVersion())) {
			throw new OptimisticEntityLockException(entity, "");
		}
		
		modelMapper.map(entity, productToUpdate);
		productRepository.save(productToUpdate);
		
		
	}

	@Override
	public void remove(Integer id) {
		Product productToDelete = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		productToDelete.setDeleted(true);
		productRepository.save(productToDelete);
	}

	@Override
	public Page<Product> getPaginatedAndSortedProducts(ListProperties listProperties) {
		Sort sort = "asc".equalsIgnoreCase(listProperties.getDirection()) ? Sort.by(listProperties.getSortBy()).ascending() : Sort.by(listProperties.getSortBy()).descending();

		Pageable pageable = PageRequest.of(listProperties.getPage(), listProperties.getSize(), sort);

		Locale locale = LocaleContextHolder.getLocale();
		
		if(locale.getLanguage().equals("tr"))
			return productRepository.findByDeletedFalseAndNameLikeIgnoreCase(pageable, "%" + listProperties.getKeyword() + "%");
		else 	if(locale.getLanguage().equals("en"))
			return productRepository.findByDeletedFalseAndEnNameLikeIgnoreCase(pageable, "%" + listProperties.getKeyword() + "%");
		else
			return productRepository.findByDeletedFalseAndEnNameLikeIgnoreCase(pageable, "%" + listProperties.getKeyword() + "%");
	}
	
	@Override
	public List<Product> getProductsByCategoryId(Short categoryId) {
        return productRepository.findByCategoryIdAndDeletedFalse(categoryId);
    }
	


}
