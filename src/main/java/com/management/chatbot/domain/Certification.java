package com.management.chatbot.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class Certification {

    private Long challengeId; // 챌린지 id

    @ElementCollection
    @Convert(converter = ListToJsonConverter.class)
    private List<CertInfo> certInfoList = new ArrayList<>();

    @Builder
    public Certification(Long challengeId, List<CertInfo> certInfoList) {
        this.challengeId = challengeId;
        this.certInfoList = certInfoList;
    }

    public void update(Long challengeId, List<CertInfo> certInfoList) {
        this.challengeId = challengeId;
        this.certInfoList = certInfoList;
    }
}
