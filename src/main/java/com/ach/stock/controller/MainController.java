package com.ach.stock.controller;

import com.ach.stock.dto.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class MainController {

    @GetMapping("/main")
    public String MainPageGo(@SessionAttribute(name = "loginUser") Users loginUser, Model model) {
        model.addAttribute("userRole", loginUser.getRole().name());
        return "main/main";
    }


}
