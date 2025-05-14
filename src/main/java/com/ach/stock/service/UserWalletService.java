package com.ach.stock.service;

import com.ach.stock.dto.UserWallet;
import com.ach.stock.dto.Users;
import com.ach.stock.repository.UserRepository;
import com.ach.stock.repository.UserWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserWalletService {

    private final UserWalletRepository userWalletRepository;

    public UserWallet findByUserId(String userId) {
        
        // 사용자 정보 조회
        return userWalletRepository.findByUserId(userId).orElse(new UserWallet());
    }

}
