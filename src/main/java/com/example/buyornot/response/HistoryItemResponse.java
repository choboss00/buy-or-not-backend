package com.example.buyornot.response;

import com.example.buyornot.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class HistoryItemResponse {
    private Long id;
    private String name;
    private String memo;
    private int price;
    private Status status;
    private LocalDateTime createdDate;
    private LocalDateTime remindDate;
}

