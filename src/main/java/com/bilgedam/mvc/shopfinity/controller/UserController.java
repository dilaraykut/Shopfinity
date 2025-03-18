package com.bilgedam.mvc.shopfinity.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bilgedam.mvc.shopfinity.model.UserEntity;
import com.bilgedam.mvc.shopfinity.service.UserReadable;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements ControllerInterface<UserEntity, Integer> {

	private final UserReadable userReadable;

	@GetMapping()
	public String getAll(Model model) {

		List<UserEntity> userList = userReadable.getList();

		model.addAttribute("userList", userList);

		return "users/index";
	}

	@Override
	public String newEntity(Model model) {

		return null;
	}

	@Override
	public String updateEntity(Integer id, Model model) {

		return null;
	}

	@Override
	public String deleteEntity(Short id) {

		return null;
	}

}
