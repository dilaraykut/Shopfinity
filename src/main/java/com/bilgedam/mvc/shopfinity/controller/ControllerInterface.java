package com.bilgedam.mvc.shopfinity.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

public interface ControllerInterface<T, ID> {
	String newEntity(Model model);
	String updateEntity(@PathVariable ID id, Model model);
	String deleteEntity(@PathVariable Short id);
}
