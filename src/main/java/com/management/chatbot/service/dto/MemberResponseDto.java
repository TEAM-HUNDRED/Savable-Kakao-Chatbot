package com.management.chatbot.service.dto;

import com.management.chatbot.domain.Member;
import com.management.chatbot.domain.Participation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class MemberResponseDto {
    private String username;
    private String kakaoName;
    private String kakaoId;
    private Long savedMoney;
    private Long reward;
    private List<Participation> participationList;
    private String phoneNumber;

    public MemberResponseDto(Member entity) {
        this.username = entity.getUsername();
        this.kakaoName = entity.getKakaoName();
        this.kakaoId = entity.getKakaoId();
        this.savedMoney = entity.getSavedMoney();
        this.reward = entity.getReward();
        this.participationList = entity.getParticipationList();
        this.phoneNumber = entity.getPhoneNumber();
    }
}
