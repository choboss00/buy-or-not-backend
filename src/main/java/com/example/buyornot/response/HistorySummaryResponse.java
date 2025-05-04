package com.example.buyornot.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HistorySummaryResponse {
    private int purchasedCount;
    private int declinedCount;
    private double declineRate;
    private int totalDeclinedAmount;
}
