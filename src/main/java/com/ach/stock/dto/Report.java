package com.ach.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Report {
    private int totalBuy;
    private int totalSell;
    private int profit;
    private double profitRate;
}
