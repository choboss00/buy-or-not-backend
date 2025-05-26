package com.example.buyornot;

import com.example.buyornot.domain.Item;
import com.example.buyornot.domain.Status;
import com.example.buyornot.repository.ItemRepository;
import com.example.buyornot.request.StatusUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
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

    private final String userId = "1";

    private Long itemId1;
    private Long itemId2;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();

        Item item1 = new Item(null, userId, "에어팟", "노이즈캔슬링", 250000,
                Status.WAITING, LocalDateTime.now().plusDays(3), LocalDateTime.now(), LocalDateTime.now());

        Item item2 = new Item(null, userId, "운동화", "러닝화 필요", 120000,
                Status.WAITING, LocalDateTime.now().plusDays(5), LocalDateTime.now(), LocalDateTime.now());


        item1 = itemRepository.save(item1);
        item2 = itemRepository.save(item2);

        itemId1 = item1.getId();
        itemId2 = item2.getId();
    }

    // 아이템 상세 조회 성공 테스트
    @Test
    void 항목_상세_조회_성공() throws Exception {
        mockMvc.perform(get("/api/items/{itemId}", itemId1)
                        .header("User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemId1.intValue())))
                .andExpect(jsonPath("$.name", is("에어팟")))
                .andExpect(jsonPath("$.price", is(250000)))
                .andExpect(jsonPath("$.memo", is("노이즈캔슬링")));
    }

    // 아이템 상세 조회 시 아이템이 없을 경우 404 응답 테스트
    @Test
    void 항목_상세_조회_아이템_없을_경우_404() throws Exception {
        Long nonExistentItemId = 999L;

        mockMvc.perform(get("/api/items/{itemId}", nonExistentItemId)
                        .header("User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // 기존의 홈 화면 조회 테스트
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

    // 항목 등록 요청이 정상적으로 처리되는지 테스트
    @Test
    void 항목_등록_요청이_정상적으로_처리된다() throws Exception {
        // given
        String jsonBody = objectMapper.writeValueAsString(
                new TestRequest("에어팟", 199000, "노이즈 캔슬링이 필요해", "전자기기", LocalDateTime.now().plusDays(7))
        );

        // when
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("User-Id", userId)
                        .content(jsonBody))
                .andExpect(status().isOk());

        // then
        // 아이템 ID 추출
        Item saved = itemRepository.findTopByOrderByCreatedDateDesc()
                .orElseThrow(() -> new RuntimeException("아이템을 찾을 수 없습니다."));

        assertThat(saved.getName()).isEqualTo("에어팟");
        assertThat(saved.getUserId()).isEqualTo(userId);
        assertThat(saved.getMemo()).isEqualTo("노이즈 캔슬링이 필요해");
    }

    // 내부 클래스으로 Request 형식 구성
    static class TestRequest {
        public String name;
        public Integer price;
        public String memo;
        public String category;
        public LocalDateTime remindDate; // 변경

        public TestRequest(String name, Integer price, String memo, String category, LocalDateTime remindDate) {
            this.name = name;
            this.price = price;
            this.memo = memo;
            this.category = category;
            this.remindDate = remindDate;
        }
    }

    @Test
    void 항목_상태_업데이트_성공() throws Exception {
        // given: 상태를 BOUGHT로 바꾸는 요청
        String patchBody = objectMapper.writeValueAsString(
                new StatusUpdateRequest(Status.PURCHASED)
        );

        // when & then
        mockMvc.perform(patch("/api/items/{itemId}", itemId1)
                        .header("User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchBody))
                .andExpect(status().isOk());

        // DB에 저장된 항목 확인
        Item updatedItem = itemRepository.findById(itemId1)
                .orElseThrow(() -> new RuntimeException("업데이트된 아이템을 찾을 수 없습니다."));

        assertThat(updatedItem.getStatus()).isEqualTo(Status.PURCHASED);
    }

    static class StatusUpdateRequest {
        public Status status;

        public StatusUpdateRequest(Status status) {
            this.status = status;
        }
    }
}
