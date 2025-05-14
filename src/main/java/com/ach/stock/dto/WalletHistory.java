package com.ach.stock.dto;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="wallet_history")
public class WalletHistory {

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Users user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "wallet_hist_seq_gen")
    @SequenceGenerator(name = "wallet_hist_seq_gen", sequenceName = "wallet_hist_seq", allocationSize = 1)
    private int id;

    @Column(name = "CHANGE_AMOUNT")
    private BigDecimal changeAmount;

    @Column(name = "CHANGE_TYPE")
    private String changeType;

    @Column(name = "BALANCE_AFTER")
    private BigDecimal balanceAfter;

    @Column(name = "MEMO")
    private String memo;

    @Column(name = "CHANGED_AT")
    private LocalDateTime changedAt;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.changedAt = LocalDateTime.now();
    }
}
