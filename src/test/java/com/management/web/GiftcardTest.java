package com.management.web;

import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.MemberResponseDto;
import com.management.web.repository.GiftcardOrderRepository;
import com.management.web.service.GiftcardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class GiftcardTest {

    @Autowired
    private GiftcardService giftcardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("기프티콘 목록 컨트롤러 테스트")
    void getGiftcardList() throws Exception {
        // given
        MemberResponseDto member = memberService.findByKakaoId("f233a5da9da3e4f070a4ccd4445dca6b66b368f558b91276218e064cb504a71afc");
        String username = member.getUsername();
        Long reward = member.getReward();

        // when

        // then
        mvc.perform(
                MockMvcRequestBuilders.get("/shop/{kakaoId}", "f233a5da9da3e4f070a4ccd4445dca6b66b368f558b91276218e064cb504a71afc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.member.username").value(username))
                .andExpect(MockMvcResultMatchers.jsonPath("$.member.reward").value(reward))
                .andDo(MockMvcResultHandlers.print());
    }
}
