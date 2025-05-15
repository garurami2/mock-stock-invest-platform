package com.ach.stock.service;

import com.ach.stock.dto.Report;
import com.ach.stock.dto.Users;
import com.ach.stock.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TradeRepository tradeRepository;

    // 각 종목별 합계 구하는 함수
    public List<Report> userTradeSummary(Users user) {
        return tradeRepository.findUserTradeSummary(user.getUserId());
    }

    // 총 합계를 구하는 함수
    public Integer userTotalTradeSummary(Users user) {
        return tradeRepository.getUserTotalTradeSummary(user.getUserId());
    }
}
