package com.management.web.api;

import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.MemberResponseDto;
import com.management.chatbot.service.dto.MemberSaveRequestDto;
import com.management.web.domain.GiftcardProduct;
import com.management.web.repository.GiftcardProductRepository;
import com.management.web.service.GiftcardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class GiftshopTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private GiftcardProductRepository giftcardProductRepository;

    @Autowired
    MockMvc mvc;

    String kakaoId = "test";

    @BeforeEach
    void setUp() {
        memberService.save(MemberSaveRequestDto.builder()
                .kakaoId(kakaoId)
                .username("test")
                .reward(0L)
                .savedMoney(0L)
                .build());

        giftcardProductRepository.save(GiftcardProduct.builder()
                .name("test")
                .price(10000L)
                .saleYN(true)
                .build());
    }
    @Test
    @DisplayName("기프티콘 목록 API 테스트")
    void getGiftcardList() throws Exception {
        // given
        MemberResponseDto member = memberService.findByKakaoId(kakaoId);
        String username = member.getUsername();
        Long reward = member.getReward();

        // when

        // then
        mvc.perform(
                        MockMvcRequestBuilders.get("/shop/{kakaoId}", kakaoId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.member.username").value(username))
                .andExpect(MockMvcResultMatchers.jsonPath("$.member.reward").value(reward))
                .andDo(MockMvcResultHandlers.print());
    }
}
