package com.example.buyornot.service;

import com.example.buyornot.domain.Item;
import com.example.buyornot.domain.Status;
import com.example.buyornot.repository.ItemRepository;
import com.example.buyornot.response.HomeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final ItemRepository itemRepository;

    public List<HomeResponse> getWaitingItems(String userId) {
        List<Item> items = itemRepository.findAllByUserIdAndStatusEnumOrderByRemindDateAsc(userId, Status.WAITING);

        return items.stream()
                .map(item -> {
                    long dDay = ChronoUnit.DAYS.between(LocalDate.now(), item.getRemindDate());
                    return new HomeResponse(
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
}

