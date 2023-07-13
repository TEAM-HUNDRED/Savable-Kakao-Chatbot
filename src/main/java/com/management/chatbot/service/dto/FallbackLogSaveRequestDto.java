package com.management.chatbot.service.dto;

import com.management.chatbot.domain.FallbackLog;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class FallbackLogSaveRequestDto {
    private String username;
    private String kakaoName;
    private String message;
    private Timestamp sentTime;

    @Builder
    public FallbackLogSaveRequestDto(String username, String kakaoName, String message, Timestamp sentTime) {
        this.username = username;
        this.kakaoName = kakaoName;
        this.message = message;
        this.sentTime = sentTime;
    }

    public FallbackLog toEntity(){
        return FallbackLog.builder()
                .username(username)
                .kakaoName(kakaoName)
                .message(message)
                .sentTime(sentTime)
                .build();
    }
}
