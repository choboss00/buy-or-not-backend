package com.example.buyornot;

import com.example.buyornot.domain.Item;
import com.example.buyornot.domain.Status;
import com.example.buyornot.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    private final String userId = "test-user-5678";

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();

        // 구매 완료 항목 2개
        itemRepository.save(new Item(null, userId, "에어팟", "샀다", 250000,
                Status.PURCHASED, LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now()));

        itemRepository.save(new Item(null, userId, "운동화", "샀다2", 120000,
                Status.PURCHASED, LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now()));

        // 참기 완료 항목 3개
        itemRepository.save(new Item(null, userId, "시계", "참았다", 300000,
                Status.DECLINED, LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now()));

        itemRepository.save(new Item(null, userId, "청바지", "참았다2", 60000,
                Status.DECLINED, LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now()));

        itemRepository.save(new Item(null, userId, "피규어", "참았다3", 150000,
                Status.DECLINED, LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    void 히스토리_서머리_API_정상_응답() throws Exception {
        mockMvc.perform(get("/api/history/summary")
                        .header("User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.purchasedCount", is(2)))
                .andExpect(jsonPath("$.declinedCount", is(3)))
                .andExpect(jsonPath("$.declineRate", is(60.0)))
                .andExpect(jsonPath("$.totalDeclinedAmount", is(510000)));
    }

    @Test
    void 구매_목록_API_정상_응답() throws Exception {
        mockMvc.perform(get("/api/history/purchased")
                        .header("User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].status", containsInAnyOrder("PURCHASED", "PURCHASED")));
    }

    @Test
    void 참기_목록_API_정상_응답() throws Exception {
        mockMvc.perform(get("/api/history/declined")
                        .header("User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].status", containsInAnyOrder("DECLINED", "DECLINED", "DECLINED")));
    }
}
