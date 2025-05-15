package com.ach.stock.service;

import com.ach.stock.dto.StockChartPricePoint;
import com.ach.stock.dto.StockPriceHistory;
import com.ach.stock.repository.StockPriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockChartService {

    final StockPriceHistoryRepository stockPriceHistoryRepository;

    public List<StockChartPricePoint> getStockPriceHistory(Long stockId, LocalDateTime filterDaysAgo) {

        List<StockPriceHistory> historyList = stockPriceHistoryRepository.findByStockIdAndRecordedAtAfterOrderByRecordedAtAsc(stockId, filterDaysAgo);


        return historyList.stream()
                .map(history -> new StockChartPricePoint(
                        history.getId(),
                        history.getStock().getStockName(),
                        history.getPrice(),
                        history.getRecordedAt()

                ))
                .collect(Collectors.toList());
    }

}
