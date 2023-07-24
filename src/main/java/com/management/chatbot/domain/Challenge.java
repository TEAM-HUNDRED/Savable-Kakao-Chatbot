package com.management.chatbot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Long duration;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "Asia/Seoul")
    private Timestamp startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "Asia/Seoul")
    private Timestamp endDate;
    private Long savedMoney;
    private Long reward;
    private String certExam;
    private Long maxCnt;
    private String thumbnail;

    @Builder
    public Challenge(String title, Long duration, Timestamp startDate, Timestamp endDate, Long savedMoney, Long reward, String certExam, Long maxCnt, String thumbnail) {
        this.title = title;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.savedMoney = savedMoney;
        this.reward = reward;
        this.certExam = certExam;
        this.maxCnt = maxCnt;
        this.thumbnail = thumbnail;
    }
}
