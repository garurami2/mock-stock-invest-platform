package com.ach.stock.controller;

import com.ach.stock.dto.StockChartPricePoint;
import com.ach.stock.service.StockChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class StockChartController {

    private final StockChartService stockChartService;

    // Chart 페이지로 이동
    @GetMapping("/stocks/chart")
    public String stockChartPage(){
        return "stocks/stockChart";
    }

    // 특정 종목 history 조회
    @GetMapping("/api/stock/{stockId}/history")
    @ResponseBody
    public List<StockChartPricePoint> getStockPriceHistory(@PathVariable Long stockId) {

        LocalDateTime filterDaysAgo = LocalDateTime.now().minusDays(7);

        return stockChartService.getStockPriceHistory(stockId, filterDaysAgo);

    }

}
