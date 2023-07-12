package com.management.chatbot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "Asia/Seoul")
    private Timestamp startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "Asia/Seoul")
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
