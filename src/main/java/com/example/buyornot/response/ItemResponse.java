package com.example.buyornot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ItemResponse {
    private Long id;
    private String name;
    private String memo;
    private Integer price;
    private LocalDate createdDate;
    private LocalDate remindDate;
    private long dDay;
}
