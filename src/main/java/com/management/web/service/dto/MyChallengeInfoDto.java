package com.management.web.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MyChallengeInfoDto {
    private String title;
    private Integer savedMoney;
    private Integer reward;
    private String username;
    private Integer cnt;

    public MyChallengeInfoDto(String title, Integer savedMoney, Integer reward, String username, Integer cnt) {
        this.title = title;
        this.savedMoney = savedMoney;
        this.reward = reward;
        this.username = username;
        this.cnt = cnt;
    }
}
