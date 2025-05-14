package com.ach.stock.controller;

import com.ach.stock.dto.Stock;
import com.ach.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
