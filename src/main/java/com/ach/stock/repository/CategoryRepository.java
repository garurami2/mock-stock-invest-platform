package com.ach.stock.repository;

import com.ach.stock.dto.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryId(String categoryId);

    void deleteByCategoryId(String categoryId);

    boolean existsByCategoryId(String categoryId);

}
