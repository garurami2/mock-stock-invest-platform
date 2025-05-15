package com.ach.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Report {
    private String stockCode;
    private String stockName;
    private Long totalQuantity;
    private Long netAmount;

    public Report(String stockCode, String stockName, Long totalQuantity, Long netAmount) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.totalQuantity = totalQuantity;
        this.netAmount = netAmount;
    }
}
