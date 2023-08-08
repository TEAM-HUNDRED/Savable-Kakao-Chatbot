package com.management.web.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyPrivateRankingInfoDto {
    private String username;
    private Integer certRank;
    private Integer certNum;
    private Integer gap;
    private Integer savedMoney;

    public MyPrivateRankingInfoDto(String username, Integer certRank, Integer certNum, Integer gap, Integer savedMoney) {
        this.username = username;
        this.certRank = certRank;
        this.certNum = certNum;
        this.gap = gap;
        this.savedMoney = savedMoney;
    }
}
