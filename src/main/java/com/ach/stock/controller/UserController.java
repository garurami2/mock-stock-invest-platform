package com.ach.stock.controller;


import com.ach.stock.dto.*;
import com.ach.stock.service.UserService;
import com.ach.stock.service.UserWalletService;
import lombok.RequiredArgsConstructor;

import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserWalletService userWalletService;

    // 회원 가입 페이지 이동
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new Users());
        return "users/signup"; // signup.html 필요
    }

    // 회원 가입
    @PostMapping("/signup")
    public String signup(@ModelAttribute UserSignUp user, Model model) {
        // 1. 회원가입시 존재하는 ID인지 확인 하는 로직
        if(userService.chkUserId(user.getUserId())){
            model.addAttribute("error", "이미 존재하는 아이디입니다.");
        }

        // 2. 회원 가입
        userService.signUp(user);

        return "login";

    }

    // 내 정보 페이지 이동
    @GetMapping("/users/info")
    public String userInfo(@SessionAttribute(name = "loginUser") Users loginUser , Model model) {

        // 보유 자산 가져오는 Service
        UserWallet userInfo = userWalletService.findByUserId(loginUser.getUserId());

        // DB에 저장된 LocalDateTime 타입 String 문자열로 변환
        LocalDateTime updatedAt = userInfo.getUpdatedAt();
        if(updatedAt != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            String formattedUpdatedAt = updatedAt.format(formatter);
            model.addAttribute("formattedUpdatedAt", formattedUpdatedAt);
        }else {
            model.addAttribute("formattedUpdatedAt", "");
        }

        model.addAttribute("userInfo", userInfo);

        return "users/user-info";
    }

}
