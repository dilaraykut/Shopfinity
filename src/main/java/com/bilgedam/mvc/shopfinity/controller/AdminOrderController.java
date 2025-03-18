package com.bilgedam.mvc.shopfinity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bilgedam.mvc.shopfinity.service.OrderReadable;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/siparisler")
@RequiredArgsConstructor
public class AdminOrderController {

	private final OrderReadable orderReadableService;

	@GetMapping
	public String listOrders(Model model) {
		model.addAttribute("orders", orderReadableService.getList());
		return "admin/siparisler/index";
	}
}
