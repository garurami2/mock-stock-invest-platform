package com.ach.stock.controller;

import com.ach.stock.dto.Category;
import com.ach.stock.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 모두 조회
    @GetMapping("/api/categories/all")
    public String categoryList(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/category/category-list";
    }

    // 카테고리 상세 조회
    @GetMapping("/admin/categories/form")
    public String categoryForm(@RequestParam(required = false) String categoryId, Model model) {
        Category category = (categoryId != null) ? categoryService.findByCategoryId(categoryId).orElse(new Category()) : new Category();
        model.addAttribute("category", category);
        return "admin/category/category-form";
    }

    // 카테고리 등록 및 수정
    @PostMapping("/admin/categories/register")
    public String categoryRegister(Category category) {
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    // 카테고리 삭제
    @PostMapping("/admin/categories/delete")
    public String categoryDelete(@RequestParam String categoryId) {
        categoryService.deleteByCategoryId(categoryId);
        return "redirect:/admin/categories";
    }
}
