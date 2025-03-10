package com.bilgedam.mvc.shopfinity.controller;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bilgedam.mvc.shopfinity.dto.ListProperties;
import com.bilgedam.mvc.shopfinity.model.Category;
import com.bilgedam.mvc.shopfinity.model.Product;
import com.bilgedam.mvc.shopfinity.service.CategoryReadable;
import com.bilgedam.mvc.shopfinity.service.CategoryWriteable;
import com.bilgedam.mvc.shopfinity.service.ProductReadable;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/kategoriler")
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryReadable categoryReadableService;
	private final CategoryWriteable categoryWriteableService;
	private final ProductReadable productReadableService;
	

	private static final String KATEGORILER_INDEX_SAYFA = "kategoriler/index";

	@GetMapping({ "", "/" }) 
	public String getAll(Model model, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "desc") String direction, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "") String keyword) {

		ListProperties listProperties = new ListProperties(sortBy, direction, page, size, keyword);

		Page<Category> categoryList = categoryReadableService.getPaginatedAndSortedCategories(listProperties);

		model.addAttribute("categoryList", categoryList);
		model.addAttribute("totalPages", categoryList.getTotalPages());
		model.addAttribute("totalElements", categoryList.getTotalElements());
		model.addAttribute("currentPage", page);
		model.addAttribute("direction", "asc".equalsIgnoreCase(direction) ? "desc" : "asc"); 
																								

		return KATEGORILER_INDEX_SAYFA;
	}

	
	@GetMapping("/yenikategori") 
	public String newCategory(Model model) {
		
		List<Category> categories = categoryReadableService.getList();
		model.addAttribute("categories",categories);
		model.addAttribute("category", new Category()); 

		return "kategoriler/yenikategori";
	}

	@PostMapping("/yenikategori") 
	public String newCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "kategoriler/yenikategori";
		}

		categoryWriteableService.add(category);

		return "redirect:/kategoriler";

	}

	@GetMapping("/kategoriduzenle/{id}")
	public String updateCategory(@PathVariable short id, Model model) {

		Category duzenlenecekKategori = categoryReadableService.getById(id);
		
		List<Category> categories = categoryReadableService.getList();
		model.addAttribute("categories",categories);

		model.addAttribute("category", duzenlenecekKategori);

		return "kategoriler/kategoriduzenle";

	}

	@PostMapping("/kategoriduzenle/{id}")
	public String updateCategory(@Valid @ModelAttribute("category") Category category, BindingResult result,
			Model model, @PathVariable short id) {
		if (result.hasErrors()) {
			return "kategoriler/kategoriduzenle/" + id;
		}
		categoryWriteableService.change(id, category);

		return "redirect:/kategoriler"; 
	}

	@GetMapping("/kategorisil/{id}")
	public String deleteCategory(@PathVariable short id) {

		categoryWriteableService.remove(id);

		return "redirect:/kategoriler";
	}

	@GetMapping("/{id}")
    public String listCategoryProducts(@PathVariable Short id, Model model) {
        // Kategoriyi getir
        Category kategori = categoryReadableService.getById(id);
        if (kategori == null) {
            return "redirect:/kategoriler"; // Kategori yoksa yönlendir
        }

        // Kategoriye ait ürünleri getir
        List<Product> urunler = productReadableService.getProductsByCategoryId(id);

        model.addAttribute("category", kategori);
        model.addAttribute("products", urunler);

        return "kategoriler/urunler"; // ✅ Yeni ürün listeleme sayfası
    }
}
