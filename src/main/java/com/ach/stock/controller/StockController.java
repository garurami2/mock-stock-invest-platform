package com.ach.stock.controller;


import com.ach.stock.dto.Category;
import com.ach.stock.dto.Stock;
import com.ach.stock.service.CategoryService;
import com.ach.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    private final CategoryService categoryService;

    // 종목 목록 페이지 이동
    @GetMapping("/admin/stocks/all")
    public String getAllStocks(Model model) {
        model.addAttribute("stocksList", stockService.getAllStocks());
        return "stocks/stock-list";
    }

    // 사용자 입장에서 종목 목록 불러오는 함수
    @GetMapping("/api/stocks/all")
    @ResponseBody
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    // 종목 상세 페이지
    @GetMapping("/stocks/{stockCode}")
    public String getStockByCode(@RequestParam String stockCode, Model model) {
        model.addAttribute("stockDetail", stockService.getStockByStock(stockCode));
        return "stocks/view";
    }

    // 종목 등록 페이지
    @GetMapping("/admin/stocks/register")
    public String registerStockPage(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "stocks/stockManagement";
    }

    // 종목 등록
    @PostMapping("/admin/stocks/register")
    public String registerStock(@ModelAttribute Stock stock, @RequestParam String categoryId, Model model) {
        stockService.registerStock(stock, categoryId);
        return "redirect:/admin/stocks/all";
    }

    // 종목 삭제
    @DeleteMapping("/admin/stocks")
    public String deleteStock(@RequestParam String stockCode, Model model) {
        stockService.deleteStock(stockCode);
        return "redirect:/admin/stocks";
    }

    // 종목 업데이트
    @PutMapping("/admin/stocks")
    public ResponseEntity<Stock> updateStock(@RequestBody Stock stock) {
        stockService.updatePrice(stock.getStockCode(), stock.getCurrentPrice());
        return ResponseEntity.ok(stock);
    }

    // 투자 시뮬레이션 API
    @PostMapping("/stocks/simulate")
    public ResponseEntity<Map<String, Object>> simulateInvestment(@RequestBody Map<String, Object> payload) {
        String stockCode = (String) payload.get("stockCode");
        int quantity = (int) payload.get("quantity");
        int buyPrice = (int) payload.get("buyPrice");

        int result = stockService.simulateInvestment(stockCode, quantity, buyPrice);
        return ResponseEntity.ok(Map.of(
                "stockCode", stockCode,
                "buyPrice", buyPrice,
                "quantity", quantity,
                "profitOrLoss", result
        ));
    }
}
