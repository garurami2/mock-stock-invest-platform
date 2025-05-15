package com.ach.stock.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stock_price_history")
public class StockPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_price_history_seq_gen")
    @SequenceGenerator(name = "stock_price_history_seq_gen", sequenceName = "stock_price_history_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

}
