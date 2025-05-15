package com.ach.stock.controller;


import com.ach.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final StockService stockService;

    @GetMapping("/dashboard")
    public String adminPage() {
        return "관리자만 접근 가능한 대시보드";
    }



}
