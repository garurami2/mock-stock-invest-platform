package com.ach.stock.controller;

import com.ach.stock.dto.Report;
import com.ach.stock.dto.StockSummary;
import com.ach.stock.dto.Users;
import com.ach.stock.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/report/summary")
    public String summaryPage(@SessionAttribute(name = "loginUser") Users loginUser, Model model) {

        List<Report> tradeSummary = reportService.userTradeSummary(loginUser);
        Integer totalTradeSummary = reportService.userTotalTradeSummary(loginUser);

        model.addAttribute("tradeSummaries", tradeSummary);
        model.addAttribute("totalTradeSummary", totalTradeSummary);

        return "report/summary-list";
    }

    @GetMapping("/api/summary")
    public List<Report> getSummary(@SessionAttribute("loginUser") Users loginUser) {
        return reportService.userTradeSummary(loginUser);
    }

}
