package com.ach.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockSummary {

    private String stockCode;
    private int buyQuantity;
    private int sellQuantity;
    private int holdingQuantity;

}
