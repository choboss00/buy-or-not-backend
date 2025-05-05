package com.example.buyornot.service;

import com.example.buyornot.domain.Item;
import com.example.buyornot.domain.FCMToken;
import com.example.buyornot.repository.FCMTokenRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final FCMTokenRepository fcmTokenRepository;

    public void sendReminder(Item item) {
        // 사용자 기기 토큰이 있다고 가정 (ex: DB에서 조회)
        String targetToken = getUserDeviceToken(item.getUserId());
        if (targetToken == null) {
            log.warn("사용자 {}의 토큰이 없어 알림을 건너뜀", item.getUserId());
            return;
        }

        String title = "오늘이 '" + item.getName() + "'의 결정일입니다!";
        String body = "지금 앱에서 구매 여부를 결정해주세요.";

        Message message = Message.builder()
                .setToken(targetToken)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("알림 전송 완료: {}", response);
        } catch (FirebaseMessagingException e) {
            log.error("푸시 알림 전송 실패", e);
        }
    }

    private String getUserDeviceToken(Long userId) {
        return fcmTokenRepository.findByUserId(userId)
                .map(FCMToken::getDeviceToken)
                .orElse(null);
    }

}
