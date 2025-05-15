package com.ach.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class StockChartPricePoint {
    private Long id;
    private String stockName;
    private Integer price;
    private LocalDateTime recordedAt;

}
