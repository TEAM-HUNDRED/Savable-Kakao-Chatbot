package com.management.chatbot.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@Embeddable
@NoArgsConstructor
public class Participation {

    private Long challengeId; // 챌린지 id
    private Integer certificationCnt;
    private Timestamp startDate;
    private Timestamp endDate;

    @Builder
    public Participation(Long challengeId, Integer certificationCnt, Timestamp startDate, Timestamp endDate) {
        this.challengeId = challengeId;
        this.certificationCnt = certificationCnt;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void update(Integer certificationCnt, Timestamp startDate, Timestamp endDate) {
        this.certificationCnt = certificationCnt;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
