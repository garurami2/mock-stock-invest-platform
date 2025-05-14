package com.ach.stock.dto;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user_wallet")
public class UserWallet {

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private Users user;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "BALANCE")
    private BigDecimal balance;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}
