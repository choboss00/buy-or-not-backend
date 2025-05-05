package com.example.buyornot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // ✅ 사용자 구분용 UUID

    private String name;

    private String memo;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime remindDate;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();  // 아이템 생성 시 자동으로 날짜를 설정
    }

}
