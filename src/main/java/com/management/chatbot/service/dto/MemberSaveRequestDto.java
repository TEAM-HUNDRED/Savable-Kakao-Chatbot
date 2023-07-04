package com.management.chatbot.service.dto;

import com.management.chatbot.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private String name;
    private String kakaoId;
    private Long savedMoney;
    private Long reward;

    @Builder
    public MemberSaveRequestDto(String name, String kakaoId, Long savedMoney, Long reward) {
        this.name = name;
        this.kakaoId = kakaoId;
        this.savedMoney = savedMoney;
        this.reward = reward;
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .kakaoId(kakaoId)
                .savedMoney(savedMoney)
                .reward(reward)
                .build();
    }
}
