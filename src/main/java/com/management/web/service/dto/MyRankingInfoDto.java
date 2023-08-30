package com.management.web.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class MyRankingInfoDto {

    private String kakaoId;
    private String username;
    private Integer certRank;

    @Builder
    public MyRankingInfoDto(String kakaoId, String username, Integer certRank) {
        this.kakaoId = kakaoId;
        this.username = username;
        this.certRank = certRank;
    }
}