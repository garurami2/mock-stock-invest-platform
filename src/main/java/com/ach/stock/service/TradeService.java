package com.ach.stock.service;

import com.ach.stock.dto.*;
import com.ach.stock.repository.CategoryRepository;
import com.ach.stock.repository.TradeRepository;
import com.ach.stock.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    public void registerTrade(StockTrade dto, Users user) {
        Users persistentUser = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Category getCategory = categoryRepository.findByCategoryId(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("존재 하지 않는 카테고리 입니다."));

        Trade trade = Trade.builder()
                .stockCode(dto.getStockCode())
                .stockName(dto.getStockName())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .tradeType(TradeType.valueOf(dto.getTradeType().toString().toUpperCase()))
                .user(persistentUser) // 영속된 객체로 설정
                .category(getCategory)
                .tradedAt(new Date())
                .build();

        tradeRepository.save(trade);
    }

    // 현재 로그인 되어 있는 사용자의 거래 전체 조회
    public List<Trade> getTradesByUser(Users user) {
        return tradeRepository.findByUser(user);
    }

    public int getUserHoldingQuantity(Users loginUser, String stockCode) {
        Integer result = tradeRepository.getUserStockQuantity(loginUser, stockCode);
        return result != null ? result : 0;
    }

}
