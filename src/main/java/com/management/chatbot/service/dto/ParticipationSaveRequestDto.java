package com.management.chatbot.service.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.chatbot.domain.CheckStatus;
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
    private Long goalCnt;
    private CheckStatus isSuccess; // 챌린지 성공 여부

    @Builder
    public ParticipationSaveRequestDto(Long challengeId, Long certificationCnt, Timestamp startDate, Timestamp endDate, Long goalCnt, CheckStatus isSuccess) {
        this.challengeId = challengeId;
        this.certificationCnt = certificationCnt;
        this.startDate = startDate;
        this.endDate = endDate;
        this.goalCnt = goalCnt;
        this.isSuccess = isSuccess;
    }

    public ParticipationSaveRequestDto(Participation entity) {
        this.challengeId = entity.getChallengeId();
        this.certificationCnt = entity.getCertificationCnt();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.goalCnt = entity.getGoalCnt();
        this.isSuccess = entity.getIsSuccess();
    }

    public Participation toEntity() {
        return Participation.builder()
                .challengeId(challengeId)
                .certificationCnt(certificationCnt)
                .startDate(startDate)
                .endDate(endDate)
                .goalCnt(goalCnt)
                .isSuccess(isSuccess)
                .build();
    }
}
