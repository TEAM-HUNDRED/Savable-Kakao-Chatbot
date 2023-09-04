package com.management.web.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
public class RankingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kakaoId;
    private Long reward;
    private Timestamp date;

    @Builder
    public RankingHistory(String kakaoId, Long reward, Timestamp date) {
        this.kakaoId = kakaoId;
        this.reward = reward;
        this.date = date;
    }
}
