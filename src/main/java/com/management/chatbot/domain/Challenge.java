package com.management.chatbot.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long period;

    private Timestamp startDate;

    private Timestamp endDate;

    private Long savedMoney;

    private Long reward;

    @Builder
    public Challenge(String title, Long period, Timestamp startDate, Timestamp endDate, Long savedMoney, Long reward) {
        this.title = title;
        this.period = period;
        this.startDate = startDate;
        this.endDate = endDate;
        this.savedMoney = savedMoney;
        this.reward = reward;
    }

    public void update(String title, Long period, Timestamp startDate, Timestamp endDate, Long savedMoney, Long reward) {
        this.title = title;
        this.period = period;
        this.startDate = startDate;
        this.endDate = endDate;
        this.savedMoney = savedMoney;
        this.reward = reward;
    }
}
