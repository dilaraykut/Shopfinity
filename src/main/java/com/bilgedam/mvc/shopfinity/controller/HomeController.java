package com.bilgedam.mvc.shopfinity.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bilgedam.mvc.shopfinity.model.Product;
import com.bilgedam.mvc.shopfinity.repository.ProductRepository;

@Controller
public class HomeController {

	private final ProductRepository productRepository;

	public HomeController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@GetMapping("/")
	public String homePage(Model model) {
		List<Product> allProducts = productRepository.findAll();

		Collections.shuffle(allProducts);
		List<Product> randomProducts = allProducts.stream().limit(4).collect(Collectors.toList());

		model.addAttribute("randomProducts", randomProducts);
		return "index";
	}
}
