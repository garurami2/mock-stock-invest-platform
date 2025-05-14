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
@Table(name = "stock")
public class Stock {

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_seq_gen")
    @SequenceGenerator(name = "stock_seq_gen", sequenceName = "stock_seq", allocationSize = 1)
    private int id;

    @Column(name = "STOCKCODE", unique = true,  nullable = false)
    private String stockCode;

    @Column(name = "STOCKNAME", unique = false,  nullable = false)
    private String stockName;

    @Column(name = "CURRENTPRICE")
    private Integer currentPrice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATEDAT")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LASTUPDATE")
    private Date lastUpdate;

    @PrePersist
    public void registerTimestamp() {
        this.createdAt = new Date();
        this.lastUpdate = new Date();
    }

    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdate = new Date();
    }
}
