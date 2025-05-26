package com.example.buyornot.controller;

import com.example.buyornot.domain.Item;
import com.example.buyornot.request.ItemRequest;
import com.example.buyornot.request.StatusUpdateRequest;
import com.example.buyornot.response.ItemDetailResponse;
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

    // 아이템 상세 조회
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDetailResponse> getItemDetail(@PathVariable("itemId") Long itemId) {
        // 아이템 정보 조회
        Item item = itemService.getItemById(itemId);
        if (item == null) {
            return ResponseEntity.notFound().build();  // 아이템이 없으면 404 반환
        }

        // 응답 DTO로 변환
        ItemDetailResponse response = new ItemDetailResponse(item);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Void> updateItemStatus(
            @PathVariable("itemId") Long itemId,
            @RequestHeader("User-Id") String userId,
            @RequestBody StatusUpdateRequest request
    ) {
        itemService.updateItemStatus(itemId, userId, request.getStatus());
        return ResponseEntity.ok().build();
    }
}
