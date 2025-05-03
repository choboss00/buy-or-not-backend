package com.example.buyornot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    private String userId; // ✅ 사용자 구분용 UUID

    private String name;

    private String memo;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private Status statusEnum;

    private LocalDate remindDate;

    private LocalDate createdDate;

    private LocalDate updatedDate;

}
