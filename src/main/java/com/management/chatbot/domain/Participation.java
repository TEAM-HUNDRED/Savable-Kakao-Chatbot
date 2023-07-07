package com.management.chatbot.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
public class Participation implements Serializable{

    private Long challengeId; // 챌린지 id
    private Long certificationCnt;
    private Timestamp startDate;
    private Timestamp endDate;

    @Builder
    public Participation(Long challengeId, Long certificationCnt, Timestamp startDate, Timestamp endDate) {
        this.challengeId = challengeId;
        this.certificationCnt = certificationCnt;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString(){
        return "Participation{" +
                "challengeId=" + challengeId +
                ", certificationCnt=" + certificationCnt +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                "}";
    }
}
