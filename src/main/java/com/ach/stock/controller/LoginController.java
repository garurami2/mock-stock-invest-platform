package com.ach.stock.controller;

import com.ach.stock.dto.Users;
import com.ach.stock.service.LoginService;
import com.ach.stock.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService usersService;

    private final JwtUtil jwtUtil;

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginError", "존재하지 않는 사용자입니다.");
        return "error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/mypage")
    public String myPage(@SessionAttribute(name = "loginUser", required = false) Users users) {
        if (users == null) return "redirect:/login";
        return "mypage";
    }
}
