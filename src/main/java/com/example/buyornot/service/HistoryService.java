package com.example.buyornot.service;

import com.example.buyornot.domain.Item;
import com.example.buyornot.domain.Status;
import com.example.buyornot.repository.ItemRepository;
import com.example.buyornot.response.HistorySummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final ItemRepository itemRepository;

    public HistorySummaryResponse getHistorySummary(String userId) {
        List<Item> purchasedItems = itemRepository.findAllByUserIdAndStatus(userId, Status.PURCHASED);
        List<Item> declinedItems = itemRepository.findAllByUserIdAndStatus(userId, Status.DECLINED);

        int purchasedCount = purchasedItems.size();
        int declinedCount = declinedItems.size();
        int totalCount = purchasedCount + declinedCount;

        double declineRate = totalCount > 0
                ? Math.round(((double) declinedCount / totalCount) * 1000) / 10.0
                : 0.0;

        int totalDeclinedAmount = declinedItems.stream()
                .mapToInt(Item::getPrice)
                .sum();

        return new HistorySummaryResponse(
                purchasedCount,
                declinedCount,
                declineRate,
                totalDeclinedAmount
        );
    }
}
