package com.example.buyornot.scheduler;

import com.example.buyornot.domain.Item;
import com.example.buyornot.repository.ItemRepository;
import com.example.buyornot.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReminderScheduler {

    private final ItemRepository itemRepository;
    private final NotificationService notificationService;

    // 매일 오후 6시 실행
    //@Scheduled(cron = "0 0 18 * * *")
    @Scheduled(cron = "0 * * * * *") // 매 분 0초마다 실행
    public void sendDailyReminders() {
        LocalDate today = LocalDate.now();

        List<Item> dueItems = itemRepository.findAllByRemindDateBetween(
                today.atStartOfDay(), today.plusDays(1).atStartOfDay()
        );

        for (Item item : dueItems) {
            notificationService.sendReminder(item);
        }

        log.info("총 {}개의 알림을 전송했습니다.", dueItems.size());
    }
}
