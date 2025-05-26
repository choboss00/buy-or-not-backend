package com.example.buyornot.service;

import com.example.buyornot.domain.Item;
import com.example.buyornot.domain.Status;
import com.example.buyornot.repository.ItemRepository;
import com.example.buyornot.request.ItemRequest;
import com.example.buyornot.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemResponse> getWaitingItems(String userId) {
        List<Item> items = itemRepository.findAllByUserIdAndStatusOrderByRemindDateAsc(userId, Status.WAITING);

        return items.stream()
                .map(item -> {
                    long dDay = item.getRemindDate() != null
                            ? ChronoUnit.DAYS.between(LocalDate.now(), item.getRemindDate().toLocalDate())
                            : 0;

                    return new ItemResponse(
                            item.getId(),
                            item.getName(),
                            item.getMemo(),
                            item.getPrice(),
                            item.getCreatedDate(),
                            item.getRemindDate(),
                            dDay
                    );
                })
                .toList();
    }

    public void createItem(String userId, ItemRequest request) {
        LocalDateTime now = LocalDateTime.now();

        Item item = new Item();
        item.setUserId(userId);
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        item.setMemo(request.getMemo());
        item.setRemindDate(request.getRemindDate() != null ? request.getRemindDate() : now.plusDays(7));
        item.setCreatedDate(now);
        item.setUpdatedDate(now);
        item.setStatus(Status.WAITING); // 기본 상태: 대기

        itemRepository.save(item);
    }

    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId).orElse(null);  // 아이템이 없으면 null 반환
    }

    public void updateItemStatus(Long itemId, String userId, Status newStatus) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));

        System.out.println("DB의 userId = " + item.getUserId());
        System.out.println("요청 userId = " + userId);

        if (!item.getUserId().toString().equals(userId)) {
            throw new IllegalStateException("해당 아이템에 대한 권한이 없습니다.");
        }

        item.setStatus(newStatus);
        item.setUpdatedDate(LocalDateTime.now());
        itemRepository.save(item);
    }
}