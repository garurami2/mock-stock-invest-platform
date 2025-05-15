package com.ach.stock.repository;


import com.ach.stock.dto.StockPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockPriceHistoryRepository extends JpaRepository<StockPriceHistory, Long> {

    List<StockPriceHistory> findByStockIdOrderByRecordedAtAsc(Long stockId);
}
