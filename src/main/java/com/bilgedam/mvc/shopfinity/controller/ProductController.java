package com.bilgedam.mvc.shopfinity.controller;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.bilgedam.mvc.shopfinity.dto.ListProperties;
import com.bilgedam.mvc.shopfinity.dto.ProductDto;
import com.bilgedam.mvc.shopfinity.enums.QuantityType;
import com.bilgedam.mvc.shopfinity.model.Category;
import com.bilgedam.mvc.shopfinity.model.Product;
import com.bilgedam.mvc.shopfinity.service.CategoryReadable;
import com.bilgedam.mvc.shopfinity.service.ProductReadable;
import com.bilgedam.mvc.shopfinity.service.ProductWriteable;
import com.bilgedam.mvc.shopfinity.service.Storeable;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/urunler")
@RequiredArgsConstructor
public class ProductController {

	private static final String URUNLER_INDEX_SAYFA = "urunler/index";
	private static final String REDIRECT_URUNLER_INDEX_SAYFA = "redirect:/urunler";
	private static final String YENI_URUN_SAYFA = "urunler/yeniurun";
	private static final String URUN_DUZENLE_SAYFA = "urunler/urunduzenle";

	private final ProductReadable productReadableService;
	private final ProductWriteable productWriteableService;
	private final CategoryReadable categoryReadableService;
	private final Storeable imageStorageService;

	private final ModelMapper modelmapper;
	
	@PostMapping("/urunduzenle/{id}")
	public String updateCategory(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result,
			Model model, @PathVariable int id, @RequestParam("img") MultipartFile img) {
		if (result.hasErrors()) {
			List<Category> categories = categoryReadableService.getList();
			
			model.addAttribute("categories", categories);
			model.addAttribute("productDto", productDto);
			model.addAttribute("quantityTypes", QuantityType.values());
			model.addAttribute("id",id);
			
			return URUN_DUZENLE_SAYFA + "/" + id;
		}

		if (img.getOriginalFilename() != null && !img.getOriginalFilename().isEmpty()) {

			try {
				imageStorageService.save(img);
				productDto.setImage(img.getOriginalFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Product product = new Product();
		
		modelmapper.map(productDto, product);
		
		product.setId(id);
		
		productWriteableService.change(id, product);

		return REDIRECT_URUNLER_INDEX_SAYFA;
	}
	
	@GetMapping("/urunduzenle/{id}")
	public String updateProduct(@PathVariable int id, Model model) {

		Product duzenlenecekUrun = productReadableService.getById(id);
		
		ProductDto productDto = new ProductDto();
		
		modelmapper.map(duzenlenecekUrun, productDto);
		
		List<Category> categories = categoryReadableService.getList();
		
		model.addAttribute("categories", categories);
		model.addAttribute("productDto", duzenlenecekUrun);
		model.addAttribute("quantityTypes", QuantityType.values());
		model.addAttribute("id",id);

		return URUN_DUZENLE_SAYFA;
	}

	@PostMapping("/yeniurun") 
	public String newProduct(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result, Model model,
			@RequestParam("img") MultipartFile img) {

		if (result.hasErrors()) {
			List<Category> categories = categoryReadableService.getList();
			model.addAttribute("categories", categories);
			model.addAttribute("productDto", new ProductDto());
			model.addAttribute("quantityTypes", QuantityType.values());
			return YENI_URUN_SAYFA;
		}

		if (img.getOriginalFilename() != null) {
			try {
				imageStorageService.save(img);
				productDto.setImage(img.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Product product = new Product();
		
		modelmapper.map(productDto, product);
		
		productWriteableService.add(product);

		return REDIRECT_URUNLER_INDEX_SAYFA;
	}

	@GetMapping("/yeniurun")
	public String newProduct(Model model) {

		List<Category> categories = categoryReadableService.getList();
		model.addAttribute("categories", categories);
		model.addAttribute("productDto", new ProductDto());
		model.addAttribute("quantityTypes", QuantityType.values());

		return YENI_URUN_SAYFA;
	}

	@GetMapping("/urunsil/{id}")
	public String deleteCategory(@PathVariable int id) {

		productWriteableService.remove(id);

		return REDIRECT_URUNLER_INDEX_SAYFA;
	}

	@GetMapping({ "", "/" })
	public String getAll(Model model, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "desc") String direction, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "") String keyword) {

		ListProperties listProperties = new ListProperties(sortBy, direction, page, size, keyword);

		Page<Product> productList = productReadableService.getPaginatedAndSortedProducts(listProperties);

		model.addAttribute("productList", productList);
		model.addAttribute("totalPages", productList.getTotalPages());
		model.addAttribute("totalElements", productList.getTotalElements());
		model.addAttribute("currentPage", page);
		model.addAttribute("direction", "asc".equalsIgnoreCase(direction) ? "desc" : "asc");
		return URUNLER_INDEX_SAYFA;
	}
	
	@GetMapping("/{id}")
	public String getProductDetail(@PathVariable int id, Model model) {
	    Product product = productReadableService.getById(id);
	    if (product == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ürün bulunamadı");
	    }
	    model.addAttribute("product", product);
	    return "urunler/detay"; // Thymeleaf şablon dosyası
	}
	
	






}
