package com.example.buyornot.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class FCMToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String deviceToken; // FCM Device Token

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public FCMToken(String userId, String deviceToken) {
        this.userId = userId;
        this.deviceToken = deviceToken;
    }

    public void updateToken(String deviceToken) {
        this.deviceToken = deviceToken;
        this.updatedAt = LocalDateTime.now();
    }
}
