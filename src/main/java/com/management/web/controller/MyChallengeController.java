package com.management.web.controller;

import com.management.web.service.dto.MyChallengeInfoDto;
import com.management.web.service.ParticipateChallengeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/challenge")
public class MyChallengeController {
    private final ParticipateChallengeService participateChallengeService;

    public MyChallengeController(ParticipateChallengeService participateChallengeService) {
        this.participateChallengeService = participateChallengeService;
    }

    @GetMapping
    public List<MyChallengeInfoDto> getMyChallengeInfo(@RequestParam String kakaoId){
        return participateChallengeService.getMyParticipateChallenge(kakaoId);
    }
}
