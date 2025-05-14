package com.ach.stock.repository;


import com.ach.stock.dto.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportRepository {

    @PersistenceContext
    private EntityManager em;

    public Integer getTotalBuyAmountByUser(Users user) {
        return (Integer) em.createQuery(
                "SELECT SUM(t.price * t.quantity) FROM Trade t WHERE t.user = :user AND t.tradeType = 'BUY'"
        ).setParameter("user", user).getSingleResult();
    }

    public Integer getTotalSellAmountByUser(Users user) {
        return (Integer) em.createQuery(
                "SELECT SUM(t.price * t.quantity) FROM Trade t WHERE t.user = :user AND t.tradeType = 'SELL'"
        ).setParameter("user", user).getSingleResult();
    }

    public List<Object[]> getStockSummaryByUser(Users user) {
        return em.createQuery(
                "SELECT t.stockCode, " +
                        "SUM(CASE WHEN t.tradeType = 'BUY' THEN t.quantity ELSE 0 END), " +
                        "SUM(CASE WHEN t.tradeType = 'SELL' THEN t.quantity ELSE 0 END) " +
                        "FROM Trade t WHERE t.user = :user GROUP BY t.stockCode"
        ).setParameter("user", user).getResultList();
    }
}
