package com.ach.stock.service;

import com.ach.stock.dto.Category;
import com.ach.stock.dto.Stock;
import com.ach.stock.dto.StockPriceHistory;
import com.ach.stock.repository.CategoryRepository;
import com.ach.stock.repository.StockPriceHistoryRepository;
import com.ach.stock.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockPriceHistoryRepository historyRepository;
    private final CategoryRepository categoryRepository;

    // 종목 전체 조회
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    // 종목 조회
    public Optional<Stock> getStockByStock(String stockCode) {
        return stockRepository.findByStockCode(stockCode);
    }

    // 종목 등록
    @Transactional
    public void registerStock(Stock stockDto, String categoryId) {

        // 카테고리가 존재하는지 검사
        Category category = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("카테고리 없음"));

        Stock stock = Stock.builder()
                .stockCode(stockDto.getStockCode())
                .stockName(stockDto.getStockName())
                .currentPrice(stockDto.getCurrentPrice())
                .category(category)
                .build();

        // 종목이 존재하는지 검사
        if(stockRepository.findByStockCode(stock.getStockCode()).isPresent()) {
           throw new IllegalArgumentException("이미 등록된 종목입니다.");
        }

        stockRepository.save(stock);
    }

    // 종목 금액 변경
    @Transactional
    public void updatePrice(String stockCode, int newPrice) {
        stockRepository.findByStockCode(stockCode).ifPresent(stock -> {
            stock.setCurrentPrice(newPrice);
            stock.setLastUpdate(new Date());
            stockRepository.save(stock);
        });
    }

    // 종목 삭제
    @Transactional
    public void deleteStock(String stockCode) {
        stockRepository.deleteByStockCode(stockCode);
    }

    // 투자 시뮬레이션 기능 (가상 매수 후 현재 시세 기준 평가)
    public int simulateInvestment(String stockCode, int quantity, int buyPrice) {
        Optional<Stock> stockOpt = stockRepository.findByStockCode(stockCode);
        if (stockOpt.isPresent()) {
            Stock stock = stockOpt.get();
            int currentPrice = stock.getCurrentPrice() != null ? stock.getCurrentPrice() : 0;
            int initialTotal = buyPrice * quantity;
            int currentTotal = currentPrice * quantity;
            return currentTotal - initialTotal; // 수익 or 손실 금액 반환
        } else {
            throw new IllegalArgumentException("해당 종목이 존재하지 않습니다: " + stockCode);
        }
    }

    @Transactional
    public void updateMockPrices(){
        List<Stock> stocks = stockRepository.findAll();

        for(Stock stock : stocks){
            double currentPrice = stock.getCurrentPrice() != null ? stock.getCurrentPrice() : 1000.0;

            // 랜덤 변동값 (-5% ~ +5%)
            double changePercent = (Math.random() * 10) - 5;
            int newPrice = (int) Math.max(100, Math.round(currentPrice * (1 + changePercent / 100)));

            // 주가 갱싱
            stock.setCurrentPrice(newPrice);
            stock.setLastUpdate(new Date());

            // 이력 저장
            StockPriceHistory history = StockPriceHistory.builder()
                    .stock(stock)
                    .price(newPrice)
                    .recordedAt(LocalDateTime.now())
                    .build();

            historyRepository.save(history);

        }

        stockRepository.saveAll(stocks);
    }

    // 종목 변동 이력 조회
    public List<StockPriceHistory> getHistoryByStock(Long stockId) {
        return historyRepository.findByStockIdOrderByRecordedAtAsc(stockId);
    }


}
