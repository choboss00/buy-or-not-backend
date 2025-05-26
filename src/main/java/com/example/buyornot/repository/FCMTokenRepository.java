package com.example.buyornot.repository;

import com.example.buyornot.domain.FCMToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FCMTokenRepository extends JpaRepository<FCMToken, Long> {
    Optional<FCMToken> findByUserIdAndDeviceToken(String userId, String deviceToken);
    List<FCMToken> findAllByUserId(String userId);

    Optional<FCMToken> findByUserId(String userId);

}
