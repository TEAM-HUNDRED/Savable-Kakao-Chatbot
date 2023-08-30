package com.management.web.controller;

import com.management.web.service.dto.RankingDto;
import com.management.web.service.RankingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }
    @GetMapping
    public RankingDto getAllRankingInfo(@RequestParam String kakaoId){
        return RankingDto.builder().privateRankingInfo(rankingService.getMyPrivateRankingInfo(kakaoId))
                .rankingListInfo(rankingService.getMyRankingInfo())
                .build();
    }
}

