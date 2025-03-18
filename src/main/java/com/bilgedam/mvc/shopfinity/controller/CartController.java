package com.bilgedam.mvc.shopfinity.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bilgedam.mvc.shopfinity.model.Product;
import com.bilgedam.mvc.shopfinity.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/sepet")
@RequiredArgsConstructor
public class CartController {

	private static final String URUNLER_INDEX_SAYFA = "sepet/index";
	private static final String REDIRECT_URUNLER_INDEX_SAYFA = "redirect:/sepet";

	private final ProductRepository productRepository;

	@ModelAttribute("addedProducts")
	public List<Product> addedProducts() {
		return new ArrayList<>();
	}

	@PostMapping("/add/{id}")
	public String addProduct(@PathVariable Integer id, HttpSession session) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ürün bulunamadı: ID = " + id));

		List<Product> addedProducts = (List<Product>) session.getAttribute("addedProducts");

		if (addedProducts == null) {
			addedProducts = new ArrayList<>();
		} else {

			boolean alreadyExists = addedProducts.stream().anyMatch(p -> p.getId().equals(product.getId()));

			if (alreadyExists) {
				return "redirect:/sepet";
			}

			addedProducts.removeIf(p -> p == null || p.getPrice() == null);
		}

		addedProducts.add(product);
		session.setAttribute("addedProducts", addedProducts);

		return "redirect:/sepet";
	}

	@GetMapping
	public String showAddedProducts(HttpSession session, Model model) {
		List<Product> addedProducts = (List<Product>) session.getAttribute("addedProducts");

		if (addedProducts == null) {
			addedProducts = new ArrayList<>();
		} else {

			addedProducts = addedProducts.stream().filter(p -> p != null && p.getPrice() != null)
					.collect(Collectors.toList());
		}

		model.addAttribute("addedProducts", addedProducts);
		return "sepet/index";
	}

	@GetMapping("/remove/{id}")
	public String removeProductFromCart(@PathVariable Integer id, HttpSession session) {
		List<Product> addedProducts = (List<Product>) session.getAttribute("addedProducts");

		if (addedProducts != null) {
			addedProducts.removeIf(product -> product.getId().equals(id));
			session.setAttribute("addedProducts", addedProducts);
		}

		return "redirect:/sepet";
	}

	@GetMapping("/clear")
	public String clearCart(HttpSession session) {
		session.removeAttribute("addedProducts");
		return "redirect:/sepet";
	}
}
