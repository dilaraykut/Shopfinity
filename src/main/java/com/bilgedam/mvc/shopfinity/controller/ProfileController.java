package com.bilgedam.mvc.shopfinity.controller;

import java.security.Principal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bilgedam.mvc.shopfinity.model.UserEntity;
import com.bilgedam.mvc.shopfinity.repository.UserRepository;
import com.bilgedam.mvc.shopfinity.service.UserReadable;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/profil")
@RequiredArgsConstructor
public class ProfileController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserReadable userReadable;

	@GetMapping
	public String getProfile(Model model, Principal principal) {
		String userEmail = principal.getName();
		UserEntity user = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı: " + userEmail));

		model.addAttribute("user", user);
		return "profil/index";
	}

	@PostMapping("/guncelle")
	public String updateProfile(UserEntity userForm, Principal principal, Model model) {
		String userEmail = principal.getName();
		UserEntity user = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı: " + userEmail));

		user.setName(userForm.getName());
		user.setSurname(userForm.getSurname());

		userRepository.save(user);
		model.addAttribute("user", user);
		model.addAttribute("successMessage", "Bilgileriniz başarıyla güncellendi.");
		return "profil/index";
	}
}
