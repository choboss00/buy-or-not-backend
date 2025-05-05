package com.example.buyornot.controller;


import com.example.buyornot.request.FCMTokenRequest;
import com.example.buyornot.service.FCMTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fcm")
public class FCMTokenController {

    private final FCMTokenService fcmTokenService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerFcmToken(@RequestHeader("User-Id") Long userId,
                                                 @RequestBody FCMTokenRequest request) {
        fcmTokenService.registerToken(userId, request.getDeviceToken());
        return ResponseEntity.ok().build();
    }
}
