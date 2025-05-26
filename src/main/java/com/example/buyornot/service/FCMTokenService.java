package com.example.buyornot.service;

import com.example.buyornot.domain.FCMToken;
import com.example.buyornot.repository.FCMTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FCMTokenService {
    private final FCMTokenRepository fcmTokenRepository;

    @Transactional
    public void saveOrUpdateToken(String userId, String token) {
        fcmTokenRepository.findByUserId(userId).ifPresentOrElse(existing -> {
            existing.updateToken(token);
            fcmTokenRepository.save(existing);
        }, () -> {
            fcmTokenRepository.save(new FCMToken(userId, token));
        });
    }

    public List<FCMToken> getTokensByUserId(String userId) {
        return fcmTokenRepository.findAllByUserId(userId);
    }
}
