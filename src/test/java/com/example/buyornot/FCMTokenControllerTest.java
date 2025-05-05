package com.example.buyornot;

import com.example.buyornot.domain.FCMToken;
import com.example.buyornot.repository.FCMTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FCMTokenControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FCMTokenRepository fcmTokenRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final Long userId = 100L;
    private final String deviceToken = "sample-fcm-token";

    @BeforeEach
    void setUp() {
        fcmTokenRepository.deleteAll();
    }

    @Test
    void FCM_토큰_등록_API_정상작동() throws Exception {
        String requestJson = objectMapper.writeValueAsString(new FcmTokenRequest(deviceToken));

        mockMvc.perform(post("/api/fcm/register")
                        .header("User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        // DB 확인
        FCMToken saved = fcmTokenRepository.findAll().get(0);
        assertThat(saved.getUserId()).isEqualTo(userId);
        assertThat(saved.getDeviceToken()).isEqualTo(deviceToken);
    }

// Removed the inner FcmTokenRequest class. The existing FCMTokenRequest DTO from the main package will be used instead.
}
