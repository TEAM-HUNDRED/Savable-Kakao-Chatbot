package com.management.chatbot.service.dto;

import com.management.chatbot.domain.Challenge;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ChallengeResponseDto {

    // 챌린지 응답 DTO
    private Long id;
    private String title;
    private Long period;
    private Timestamp startDate;
    private Timestamp endDate;
    private Long savedMoney;
    private Long reward;
    private String certExam;

    public ChallengeResponseDto(Challenge entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.period = entity.getPeriod();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.savedMoney = entity.getSavedMoney();
        this.reward = entity.getReward();
        this.certExam = entity.getCertExam();
    }
}
