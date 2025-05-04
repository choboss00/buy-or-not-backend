package com.example.buyornot;

import com.example.buyornot.domain.Item;
import com.example.buyornot.domain.Status;
import com.example.buyornot.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ObjectMapper objectMapper;

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
        mockMvc.perform(get("/api/items")
                        .header("User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // WAITING 상태인 항목 2개
                .andExpect(jsonPath("$[0].name", is("에어팟")))
                .andExpect(jsonPath("$[1].name", is("운동화")));
    }

    @Test
    void 항목_등록_요청이_정상적으로_처리된다() throws Exception {
        // given
        String jsonBody = objectMapper.writeValueAsString(
                new TestRequest("에어팟", 199000, "노이즈 캔슬링이 필요해", "전자기기", LocalDate.now().plusDays(7))
        );

        // when
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("User-Id", userId)
                        .content(jsonBody))
                .andExpect(status().isOk());

        // then
        Item saved = itemRepository.findAll().get(3);
        assertThat(saved.getName()).isEqualTo("에어팟");
        assertThat(saved.getUserId()).isEqualTo(userId);
        assertThat(saved.getMemo()).isEqualTo("노이즈 캔슬링이 필요해");
    }

    // 내부 클래스로 Request 형식 구성
    static class TestRequest {
        public String name;
        public Integer price;
        public String memo;
        public String category;
        public LocalDate remindDate;

        public TestRequest(String name, Integer price, String memo, String category, LocalDate remindDate) {
            this.name = name;
            this.price = price;
            this.memo = memo;
            this.category = category;
            this.remindDate = remindDate;
        }
    }
}
