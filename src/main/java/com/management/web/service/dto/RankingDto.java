package com.management.web.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RankingDto {
    private MyPrivateRankingInfoDto privateRankingInfo;
    private List<MyRankingInfoDto> rankingListInfo;

    public RankingDto(MyPrivateRankingInfoDto privateRankingInfo, List<MyRankingInfoDto> rankingListInfo){
        this.privateRankingInfo = privateRankingInfo;
        this.rankingListInfo = rankingListInfo;
    }
}