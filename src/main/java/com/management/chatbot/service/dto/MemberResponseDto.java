package com.management.chatbot.service.dto;

import com.management.chatbot.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MemberResponseDto {
    private String name;
    private String kakaoId;
    private Long savedMoney;
    private Long reward;

    public MemberResponseDto(Member entity) {
        this.name = entity.getName();
        this.kakaoId = entity.getKakaoId();
        this.savedMoney = entity.getSavedMoney();
        this.reward = entity.getReward();
    }
}
