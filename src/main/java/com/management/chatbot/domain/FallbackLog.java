package com.management.chatbot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Getter
public class FallbackLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String kakaoName;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "Asia/Seoul")
    private Timestamp sentTime;

    @Builder
    public FallbackLog(String username, String kakaoName, String message, Timestamp sentTime) {
        this.username = username;
        this.kakaoName = kakaoName;
        this.message = message;
        this.sentTime = sentTime;
    }
}
