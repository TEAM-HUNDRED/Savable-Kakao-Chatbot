package com.management.web.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MyMainInfoDto {
    private String kakaoId;
    private String username;
    private Integer savedMoney;
    private Integer reward;

    @Builder
    public MyMainInfoDto(String kakaoId, String username, Integer savedMoney, Integer reward) {
        this.kakaoId = kakaoId;
        this.username = username;
        this.savedMoney = savedMoney;
        this.reward = reward;
    }
}
