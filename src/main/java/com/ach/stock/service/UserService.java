package com.ach.stock.service;


import com.ach.stock.dto.Role;
import com.ach.stock.dto.UserSignUp;
import com.ach.stock.dto.UserWallet;
import com.ach.stock.dto.Users;
import com.ach.stock.repository.UserRepository;

import com.ach.stock.repository.UserWalletRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     *
     * 회원 가입
     *
     */
    @Transactional
    public void signUp(UserSignUp dto) {

        // 사용자 정보
        Users user = new Users();
        user.setUserId(dto.getUserId());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setNickName(dto.getNickName());
        user.setRole(Role.valueOf(dto.getRole()));
        user.setCreatedAt(new Date());

        // 사용자 보유 금액 저장
        UserWallet wallet = new UserWallet();
        wallet.setUser(user);
        wallet.setUserId(dto.getUserId());
        wallet.setBalance(dto.getBalance());
        wallet.setUpdatedAt(LocalDateTime.now());

        user.setUserWallet(wallet); // 반드시 설정해야 JPA cascade로 함께 저장됨

        userRepository.save(user); // 여기서 Wallet도 같이 저장됨

    }

    /**
     *
     * 사용자가 존재하는지 체크
     *
     * @param userId
     * @return
     */
    public boolean chkUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

}
