package com.bilgedam.mvc.shopfinity.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bilgedam.mvc.shopfinity.model.Order;
import com.bilgedam.mvc.shopfinity.model.Product;
import com.bilgedam.mvc.shopfinity.model.UserEntity;
import com.bilgedam.mvc.shopfinity.repository.ProductRepository;
import com.bilgedam.mvc.shopfinity.repository.UserRepository;
import com.bilgedam.mvc.shopfinity.service.OrderReadable;
import com.bilgedam.mvc.shopfinity.service.OrderWriteable;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/siparisler")
@RequiredArgsConstructor
public class OrderController {

	private final OrderReadable orderReadableService;
	private final OrderWriteable orderWriteableService;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	@PostMapping("/create")
	public String createOrder(@RequestParam("products") List<Long> productIds, HttpSession session,
			RedirectAttributes redirectAttributes, Principal principal) {

		if (productIds.isEmpty()) {
			return "redirect:/sepet?error=empty";
		}

		List<Product> products = productRepository.getProductsByIds(productIds);

		if (products.isEmpty()) {
			return "redirect:/sepet?error=no-products";
		}

		Order newOrder = new Order();

		newOrder.setProducts(products);

		UserEntity user = userRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı: " + principal.getName()));
		newOrder.setInsertedUser(user);

		newOrder.setPurchaseDate(LocalDateTime.now());

		double totalPrice = products.stream().mapToDouble(p -> p.getPrice().doubleValue()).sum();

		newOrder.setTotalPrice(totalPrice);
		newOrder.setPrice(totalPrice);

		orderWriteableService.add(newOrder);

		session.setAttribute("addedProducts", new ArrayList<>());

		redirectAttributes.addFlashAttribute("message", "Siparişiniz başarıyla oluşturuldu!");

		return "redirect:/siparisler";
	}

	@GetMapping("/{id}")
	public String getOrderById(@PathVariable Long id, Model model) {
		Order order = orderReadableService.getById(id);
		if (order == null) {
			throw new EntityNotFoundException("Sipariş bulunamadı!");
		}
		model.addAttribute("order", order);
		return "siparisler/detay";
	}

	@PostMapping("/{id}/delete")
	public String deleteOrder(@PathVariable Long id) {
		orderWriteableService.remove(id);
		return "redirect:/siparisler";
	}

	@GetMapping
	public String listOrders(Model model, Principal principal) {

		String userEmail = principal.getName();

		List<Order> userOrders = orderReadableService.getByInsertedUserEmail(userEmail);

		model.addAttribute("orders", userOrders);
		return "siparisler/index";
	}

}
