package com.example.buyornot.controller;

import com.example.buyornot.request.ItemRequest;
import com.example.buyornot.response.ItemResponse;
import com.example.buyornot.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getHomeItems(
            @RequestHeader("User-Id") String userId) {

        List<ItemResponse> items = itemService.getWaitingItems(userId);
        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<Void> createItem(
            @RequestHeader("User-Id") String userId,
            @RequestBody ItemRequest request) {

        itemService.createItem(userId, request);
        return ResponseEntity.ok().build();
    }
}
