package com.ach.stock.repository;

import com.ach.stock.dto.WalletHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletHistoryRepository extends JpaRepository<WalletHistory, Long> {
}
