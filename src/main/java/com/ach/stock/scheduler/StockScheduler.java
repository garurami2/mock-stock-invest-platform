package com.ach.stock.scheduler;

import com.ach.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class StockScheduler {
    private final StockService stockService;

    // 매 10초마다 시세 랜덤 업데이트
    @Scheduled(fixedRate = 10000)
    public void updateStockPrices() {
        stockService.updateMockPrices();
    }
}
