package com.ach.stock.service;

import com.ach.stock.dto.Category;
import com.ach.stock.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 전체 목록 조회
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    // 단건 조회 (수정용)
    public Optional<Category> findByCategoryId(String categoryId) {
        return categoryRepository.findByCategoryId(categoryId);
    }

    // 저장(등록/수정 겸용)
    public void save(Category input) {

        Optional<Category> existing = categoryRepository.findByCategoryId(input.getCategoryId());

        // 수정
        if(existing.isPresent()) {
            Category category = existing.get();
            category.setCategoryName(input.getCategoryName()); // 이름만 수정
            categoryRepository.save(category);
        }else{
            categoryRepository.save(input);
        }
    }

    // 삭제
    public void deleteByCategoryId(String categoryId) {
        categoryRepository.deleteByCategoryId(categoryId);
    }

    // 중복 검사용(등록시)
    public boolean existsByCategoryId(String categoryId) {
        return categoryRepository.existsByCategoryId(categoryId);
    }

}
