package com.example.buyornot.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ItemRequest {
    private String name;
    private Integer price;
    private String memo;
    private String category;
    private LocalDateTime remindDate; // 클라이언트에서 선택 가능, 없으면 서버에서 7일 후로 설정
}
