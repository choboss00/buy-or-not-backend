package com.example.buyornot.controller;

import com.example.buyornot.response.HomeResponse;
import com.example.buyornot.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    public ResponseEntity<List<HomeResponse>> getHomeItems(
            @RequestHeader("User-Id") String userId) {

        List<HomeResponse> items = homeService.getWaitingItems(userId);
        return ResponseEntity.ok(items);
    }
}
