package com.example.buyornot.controller;

import com.example.buyornot.response.HistorySummaryResponse;
import com.example.buyornot.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/summary")
    public HistorySummaryResponse getHistorySummary(@RequestHeader("User-Id") String userId) {
        return historyService.getHistorySummary(userId);
    }
}

