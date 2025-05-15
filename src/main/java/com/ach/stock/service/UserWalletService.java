package com.ach.stock.service;

import com.ach.stock.dto.StockTrade;
import com.ach.stock.dto.TradeType;
import com.ach.stock.dto.UserWallet;
import com.ach.stock.dto.Users;
import com.ach.stock.repository.UserRepository;
import com.ach.stock.repository.UserWalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserWalletService {

    private final UserWalletRepository userWalletRepository;

    public UserWallet findByUserId(String userId) {
        // 사용자 정보 조회
        return userWalletRepository.findByUserId(userId).orElse(new UserWallet());
    }

    // 매도/매수 후 사용자 금액 변경
    @Transactional
    public void updateBalance(Users loginUser, StockTrade trade) {

        UserWallet getUserWallet = userWalletRepository.findByUserId(loginUser.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자 금액 조회중 에러 발생"));

        BigDecimal userBalance = getUserWallet.getBalance();
        BigDecimal tradePrice = BigDecimal.valueOf(trade.getPrice());

        if(userBalance.compareTo(tradePrice) < 0) { // 가지고 있는 금액이 구매하려는 금액보다 적을 경우
            throw new RuntimeException("소유하신 금액보다 구매하려는 금액이 더 큽니다.");
        }else{
            BigDecimal newBalance = null;

            if(trade.getTradeType() == TradeType.BUY) {// 매수
                newBalance = userBalance.subtract(tradePrice);
            }else{// 매도
                newBalance = userBalance.add(tradePrice);
            }

            getUserWallet.setBalance(userBalance.subtract(tradePrice));
            getUserWallet.setUpdatedAt(LocalDateTime.now());
        }
    }
}
