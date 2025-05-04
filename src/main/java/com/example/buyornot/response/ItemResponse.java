package com.example.buyornot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ItemResponse {
    private Long id;
    private String name;
    private String memo;
    private Integer price;
    private LocalDateTime createdDate;
    private LocalDateTime remindDate;
    private long dDay;
}
