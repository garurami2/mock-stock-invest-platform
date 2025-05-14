package com.ach.stock.repository;

import com.ach.stock.dto.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    // 상세 종목 가져오기
    Optional<Stock> findByStockCode(String stockCode);

    void deleteByStockCode(String stockCode);
}
