package com.ach.stock.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Orders {
    private int id;
    private int userId;
    private int stockId;
    private String type;
    private int quantity;
    private int priceAtOrder;
    private Date orderTime;

}
