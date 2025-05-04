package com.example.buyornot.service;

import com.example.buyornot.domain.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public void sendReminder(Item item) {
        String message = String.format("오늘은 '%s'에 대한 결정일입니다!", item.getName());

        // TODO: 실제 푸시 알림 연동 로직 (예: Firebase FCM)
        log.info("[알림 전송] 사용자 ID: {}, 메시지: {}", item.getUserId(), message);
    }
}
