package com.management.chatbot.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyMainInfoDto {
    private String username;
    private Integer savedMoney;
    private Integer reward;

    public MyMainInfoDto(String username, Integer savedMoney, Integer reward) {
        this.username = username;
        this.savedMoney = savedMoney;
        this.reward = reward;
    }
}
