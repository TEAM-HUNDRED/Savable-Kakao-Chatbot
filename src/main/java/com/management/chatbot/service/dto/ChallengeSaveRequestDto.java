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
    private Long period;
    private Timestamp startDate;
    private Timestamp endDate;
    private Long savedMoney;
    private Long reward;

    @Builder
    public ChallengeSaveRequestDto(String title, Long period, Timestamp startDate, Timestamp endDate, Long savedMoney, Long reward) {
        this.title = title;
        this.period = period;
        this.startDate = startDate;
        this.endDate = endDate;
        this.savedMoney = savedMoney;
        this.reward = reward;
    }

    public Challenge toEntity() {
        return Challenge.builder()
                .title(title)
                .period(period)
                .startDate(startDate)
                .endDate(endDate)
                .savedMoney(savedMoney)
                .reward(reward)
                .build();
    }
}
