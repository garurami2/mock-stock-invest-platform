package com.ach.stock.repository;

import com.ach.stock.dto.Report;
import com.ach.stock.dto.Trade;
import com.ach.stock.dto.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    // 현재 로그인 되어 있는 사용자의 거래 전체 조회
    List<Trade> findByUser(Users user);

    @Query("""
            SELECT SUM(CASE WHEN t.tradeType = 'BUY' THEN t.quantity ELSE -t.quantity END)
              FROM Trade t WHERE t.user = :user AND t.stockCode = :stockCode
           """)
    Integer getUserStockQuantity(@Param("user") Users user, @Param("stockCode") String stockCode);

    @Query("""
                SELECT new com.ach.stock.dto.Report(
                    t.stockCode,
                    t.stockName,
                    SUM(CASE WHEN t.tradeType = 'BUY' THEN t.quantity ELSE -t.quantity END),
                    SUM(CASE WHEN t.tradeType = 'BUY' THEN t.price * t.quantity ELSE -t.price * t.quantity END)
                )
                FROM Trade t
                WHERE t.user.userId = :userId
                GROUP BY t.user.userId, t.stockCode, t.stockName
            """)
    List<Report> findUserTradeSummary(@Param("userId") String userId);

    @Query("""
            SELECT SUM(CASE WHEN t.tradeType = 'BUY' THEN (t.price * t.quantity)ELSE (-t.price * t.quantity) END)
              FROM Trade t WHERE t.user.userId = :userId
           """)
    Integer getUserTotalTradeSummary(@Param("userId") String userId);
}
