package com.bilgedam.mvc.shopfinity.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bilgedam.mvc.shopfinity.enums.Role;
import com.bilgedam.mvc.shopfinity.model.UserEntity;
import com.bilgedam.mvc.shopfinity.repository.UserRepository;
import com.bilgedam.mvc.shopfinity.service.UserReadable;
import com.bilgedam.mvc.shopfinity.service.UserWriteable;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/kullanicilar")
@RequiredArgsConstructor
public class AdminUserController {

	private final UserReadable userReadableService;
	private final UserWriteable userWriteableService;
	private final UserRepository userRepository;

	@GetMapping
	public String listUsers(Model model) {
		List<UserEntity> userList = userReadableService.getList();
		model.addAttribute("userList", userList);
		return "admin/kullanicilar/index";
	}

	@PostMapping("/sil/{id}")
	public String deleteUser(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		try {
			userWriteableService.remove(id);
			redirectAttributes.addFlashAttribute("success", "Kullanıcı başarıyla silindi!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Kullanıcı silinirken bir hata oluştu!");
		}
		return "redirect:/admin/kullanicilar";
	}

	@PostMapping("/rol-guncelle")
	public String updateUserRole(@RequestParam("userId") Integer userId, @RequestParam("newRole") String newRole,
			RedirectAttributes redirectAttributes) {

		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı"));

		try {
			Role role = Role.valueOf(newRole);
			user.setRole(role);
			userRepository.save(user);

			redirectAttributes.addFlashAttribute("successMessage", "Rol başarıyla güncellendi.");
		} catch (IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Geçersiz rol seçimi.");
		}

		return "redirect:/admin/kullanicilar";
	}

}
