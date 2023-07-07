package com.management.chatbot.service.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.chatbot.domain.Participation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashMap;

@Getter
@NoArgsConstructor
public class ParticipationSaveRequestDto {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Long challengeId; // 챌린지 id
    private Long certificationCnt;
    private Timestamp startDate;
    private Timestamp endDate;

    @Builder
    public ParticipationSaveRequestDto(Long challengeId, Long certificationCnt, Timestamp startDate, Timestamp endDate) {
        this.challengeId = challengeId;
        this.certificationCnt = certificationCnt;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Participation toEntity() {
        return Participation.builder()
                .challengeId(challengeId)
                .certificationCnt(certificationCnt)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
