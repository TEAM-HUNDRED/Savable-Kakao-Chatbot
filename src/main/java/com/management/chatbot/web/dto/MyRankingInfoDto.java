package com.management.chatbot.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyRankingInfoDto {

    private String username;
    private Integer certRank;

    public MyRankingInfoDto(String username, Integer certRank) {
        this.username = username;
        this.certRank = certRank;
    }
}