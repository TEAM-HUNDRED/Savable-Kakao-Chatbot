package com.management.web.controller;

import com.management.web.service.dto.MyChallengeInfoDto;
import com.management.web.service.ParticipateChallengeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/challenge")
public class MyChallengeController {
    private final ParticipateChallengeService participateChallengeService;

    public MyChallengeController(ParticipateChallengeService participateChallengeService) {
        this.participateChallengeService = participateChallengeService;
    }

    @GetMapping("/{kakaoId}")
    public List<MyChallengeInfoDto> getMyChallengeInfo(@PathVariable String kakaoId){
        return participateChallengeService.getMyParticipateChallenge(kakaoId);
    }

}
