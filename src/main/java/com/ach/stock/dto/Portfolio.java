package com.ach.stock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Portfolio {
    private int id;
    private int userId;
    private int stockId;
    private int quantity;
    private int averagePrice;

}
