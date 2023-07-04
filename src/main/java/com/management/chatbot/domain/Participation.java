package com.management.chatbot.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
public class Participation implements Serializable{

    private Long challengeId; // 챌린지 id
    private Integer certificationCnt;
    private Timestamp startDate;
    private Timestamp endDate;

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
