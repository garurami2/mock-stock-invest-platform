package com.ach.stock.service;

import com.ach.stock.dto.Report;
import com.ach.stock.dto.StockSummary;
import com.ach.stock.dto.Users;
import com.ach.stock.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    // 현재 보유한 총 자산
    public Report getSummaryReport(Users user) {
        int totalBuy = Optional.ofNullable(reportRepository.getTotalBuyAmountByUser(user)).orElse(0);
        int totalSell = Optional.ofNullable(reportRepository.getTotalSellAmountByUser(user)).orElse(0);
        int profit = totalSell - totalBuy;

        double profitRate = totalBuy == 0 ? 0.0 : ((double) profit / totalBuy) * 100;

        return new Report(totalBuy, totalSell, profit, profitRate);
    }

    // 종목별 총 자산
    public List<StockSummary> getStockSummaries(Users user) {
        List<Object[]> rawList = reportRepository.getStockSummaryByUser(user);
        return rawList.stream().map(row -> {
            String stockCode = (String) row[0];
            int buy = ((Number) row[1]).intValue();
            int sell = ((Number) row[2]).intValue();
            return new StockSummary(stockCode, buy, sell, buy - sell);
        }).toList();
    }

}
