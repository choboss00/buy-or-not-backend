package com.example.buyornot.response;

import com.example.buyornot.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@AllArgsConstructor
public class ItemDetailResponse {
    private Long id;
    private String name;
    private String memo;
    private Integer price;
    private LocalDateTime createdDate;
    private long dDay;

    public ItemDetailResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.memo = item.getMemo();
        this.createdDate = item.getCreatedDate();
        this.dDay = item.getRemindDate() != null
                ? ChronoUnit.DAYS.between(LocalDate.now(), item.getRemindDate().toLocalDate())
                : 0;
    }
}

