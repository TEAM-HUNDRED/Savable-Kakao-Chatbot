package com.management.chatbot.service.dto;

import com.management.chatbot.domain.Challenge;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class ChallengeSaveRequestDto {

    private String title;
    private Long duration;
    private Timestamp startDate;
    private Timestamp endDate;
    private Long savedMoney;
    private Long reward;
    private Long maxCnt;
    private String thumbnail;

    @Builder
    public ChallengeSaveRequestDto(String title, Long duration, Timestamp startDate, Timestamp endDate, Long savedMoney, Long reward, Long maxCnt, String thumbnail) {
        this.title = title;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.savedMoney = savedMoney;
        this.reward = reward;
        this.maxCnt = maxCnt;
        this.thumbnail = thumbnail;
    }

    public Challenge toEntity() {
        return Challenge.builder()
                .title(title)
                .duration(duration)
                .startDate(startDate)
                .endDate(endDate)
                .savedMoney(savedMoney)
                .reward(reward)
                .maxCnt(maxCnt)
                .thumbnail(thumbnail)
                .build();
    }
}
