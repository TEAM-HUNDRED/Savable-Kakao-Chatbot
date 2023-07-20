package com.management.chatbot.web.controller;

import com.management.chatbot.web.dto.RankingDto;
import com.management.chatbot.web.service.RankingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

