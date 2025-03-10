package com.bilgedam.mvc.shopfinity.controller;

import java.net.URLEncoder;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.bilgedam.mvc.shopfinity.service.impl.ImageStorageService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
	
	@Autowired
	private ImageStorageService imageStorageService;
	
	@GetMapping("img/{fileName:.+}")
	public ResponseEntity<Resource> getImage(@PathVariable String fileName) {

		Resource file = imageStorageService.load(fileName);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	@GetMapping("/locale")
	public String getLocale(@RequestParam String language, HttpServletRequest request) {
		// service katmanında seçilen dil gerekli olduğunda kullanmak için
		// bu nesneye dil ayarını vermek gerekir.
		LocaleContextHolder.setLocale(new Locale(language));
		
		String referer = request.getHeader("referer");
		
		return "redirect:"+ referer; 
	}
	
	
	
	@GetMapping("/search")
	public String search(String keyword) {
		keyword = URLEncoder.encode(keyword);
		return "redirect:/kategoriler?keyword=" + keyword;
	}
	
	
}
