package com.management.web.api;

import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.MemberResponseDto;
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
    MockMvc mvc;

    @Test
    @DisplayName("기프티콘 목록 API 테스트")
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
