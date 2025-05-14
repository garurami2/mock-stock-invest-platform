package com.ach.stock.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class StockTrade {

    private String stockCode;
    private String stockName;
    private int price;
    private int quantity;
    private TradeType tradeType;
    private Date tradedAt;
    private String categoryId;

}
