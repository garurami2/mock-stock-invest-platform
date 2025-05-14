package com.ach.stock.controller;

import com.ach.stock.dto.Report;
import com.ach.stock.dto.StockSummary;
import com.ach.stock.dto.Users;
import com.ach.stock.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/api/summary")
    public ResponseEntity<Report> getSummary(@SessionAttribute("loginUser") Users loginUser) {
        return ResponseEntity.ok(reportService.getSummaryReport(loginUser));
    }

    @GetMapping("/api/by-stock")
    public ResponseEntity<List<StockSummary>> getByStock(@SessionAttribute("loginUser") Users loginUser) {
        return ResponseEntity.ok(reportService.getStockSummaries(loginUser));
    }

}
