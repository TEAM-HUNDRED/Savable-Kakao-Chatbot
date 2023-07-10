package com.management.chatbot.service.dto;

import com.management.chatbot.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private String username;
    private String kakaoId;
    private Long savedMoney;
    private Long reward;

    @Builder
    public MemberSaveRequestDto(String username, String kakaoId, Long savedMoney, Long reward) {
        this.username = username;
        this.kakaoId = kakaoId;
        this.savedMoney = savedMoney;
        this.reward = reward;
    }

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .kakaoId(kakaoId)
                .savedMoney(savedMoney)
                .reward(reward)
                .build();
    }
}
