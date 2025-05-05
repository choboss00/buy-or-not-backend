package com.example.buyornot.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FCMTokenRequest {
    private String deviceToken;
}
