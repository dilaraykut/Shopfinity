package com.bilgedam.mvc.shopfinity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

	private static final String LOGIN = "login";
	private static final String REDIRECT_LOGIN = "redirect:/login";

	@GetMapping("/login")
	public String login() {
		return LOGIN;
	}

	@PostMapping("/logout")
	public String logout() {
		return REDIRECT_LOGIN;
	}

}