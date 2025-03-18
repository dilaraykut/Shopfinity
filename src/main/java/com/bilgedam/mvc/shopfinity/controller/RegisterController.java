package com.bilgedam.mvc.shopfinity.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bilgedam.mvc.shopfinity.enums.Role;
import com.bilgedam.mvc.shopfinity.model.UserEntity;
import com.bilgedam.mvc.shopfinity.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RegisterController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@GetMapping("/kayit")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new UserEntity());
		return "auth/kayit";
	}

	@PostMapping("/kayit")
	public String registerUser(@ModelAttribute("user") UserEntity user, RedirectAttributes redirectAttributes) {

		if (userRepository.existsByEmail(user.getEmail())) {
			redirectAttributes.addFlashAttribute("errorMessage", "Bu e-posta zaten kullanılıyor!");
			return "redirect:/kayit";
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		user.setRole(Role.ROLE_USER);

		userRepository.save(user);

		redirectAttributes.addFlashAttribute("successMessage", "Başarıyla kayıt oldunuz! Lütfen giriş yapın.");
		return "redirect:/login";
	}
}
