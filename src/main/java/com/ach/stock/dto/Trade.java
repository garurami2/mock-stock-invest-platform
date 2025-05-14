package com.ach.stock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trade")
public class Trade {

    @ManyToOne
    @JoinColumn(name = "USERID")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trade_seq_gen")
    @SequenceGenerator(name = "trade_seq_gen", sequenceName = "trade_seq", allocationSize = 1)
    private int id;

    @Column(name = "STOCK_CODE", nullable = false)
    private String stockCode;

    @Column(name = "STOCK_NAME", nullable = false)
    private String stockName;

    @Column(name = "PRICE", nullable = false)
    private int price;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRADE_TYPE", nullable = false)
    private TradeType tradeType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRADED_AT")
    private Date tradedAt;

}
