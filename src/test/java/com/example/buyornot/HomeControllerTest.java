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

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    private final String userId = "test-user-1234";

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();

        Item item1 = new Item(null, userId, "에어팟", "노이즈캔슬링", 250000,
                Status.WAITING, LocalDate.now().plusDays(3), LocalDate.now(), LocalDate.now());

        Item item2 = new Item(null, userId, "운동화", "러닝화 필요", 120000,
                Status.WAITING, LocalDate.now().plusDays(5), LocalDate.now(), LocalDate.now());

        Item item3 = new Item(null, userId, "청바지", "필요 없음", 80000,
                Status.DECLINED, LocalDate.now().plusDays(2), LocalDate.now(), LocalDate.now());

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
    }

    @Test
    void 홈_화면_조회_성공() throws Exception {
        mockMvc.perform(get("/api/home")
                        .header("User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // WAITING 상태인 항목 2개
                .andExpect(jsonPath("$[0].name", is("에어팟")))
                .andExpect(jsonPath("$[1].name", is("운동화")));
    }
}
