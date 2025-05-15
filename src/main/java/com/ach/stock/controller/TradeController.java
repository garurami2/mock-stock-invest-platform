package com.ach.stock.controller;


import com.ach.stock.dto.StockTrade;
import com.ach.stock.dto.Trade;
import com.ach.stock.dto.UserWallet;
import com.ach.stock.dto.Users;
import com.ach.stock.service.TradeService;
import com.ach.stock.service.UserService;
import com.ach.stock.service.UserWalletService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;
    private final UserService userService;
    private final UserWalletService userWalletService;

    // 매도/매수 페이지 호출
    @GetMapping("/trades/center")
    public String newTradeForm(@SessionAttribute(name = "loginUser") Users loginUser, Model model) {
        return "trade/trade-center";
    }

    // 현재 로그인된 사용자의 거래 내역 목록 조회 및 이동
    @GetMapping("/trades/my")
    public String getMyTrades(@SessionAttribute(name = "loginUser") Users loginUser, Model model) {
        List<Trade> myTrades = tradeService.getTradesByUser(loginUser);
        model.addAttribute("trades", myTrades);
        model.addAttribute("userRole", loginUser.getRole().name());
        return "trade/trade-list";
    }

    // 현재 보유중인 주문 개수 가져오는 함수
    @GetMapping("/trades/holding/{stockCode}")
    @ResponseBody
    public Integer holding(@PathVariable String stockCode,
                           @SessionAttribute(name = "loginUser") Users loginUser) {
        return tradeService.getUserHoldingQuantity(loginUser, stockCode);
    }

    // 매도/매수 함수
    @PostMapping("/api/trade")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> registerTrade(@RequestBody StockTrade trade, @SessionAttribute(name = "loginUser") Users loginUser) {
        // 거래로 인한 사용자의 금액 변경
        userWalletService.updateBalance(loginUser, trade);

        // 거래내역 저장
        tradeService.registerTrade(trade, loginUser);

        return ResponseEntity.ok("Trade Successfully Registered");
    }

}
