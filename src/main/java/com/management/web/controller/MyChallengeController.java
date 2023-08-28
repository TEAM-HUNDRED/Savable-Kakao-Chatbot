package com.management.web.controller;

import com.management.web.service.dto.MyChallengeCertDto;
import com.management.web.service.dto.MyChallengeInfoDto;
import com.management.web.service.ParticipateChallengeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/challenges")
public class MyChallengeController {
    private final ParticipateChallengeService participateChallengeService;

    public MyChallengeController(ParticipateChallengeService participateChallengeService) {
        this.participateChallengeService = participateChallengeService;
    }

    @GetMapping("/users/{kakaoId}")
    public List<MyChallengeInfoDto> getMyChallengeInfo(@PathVariable String kakaoId){
        return participateChallengeService.getMyParticipateChallenge(kakaoId);
    }
    @GetMapping("{challengeId}/users/{kakaoId}")
    public List<MyChallengeCertDto> getMyChallengeInfo(@PathVariable Integer challengeId, @PathVariable String kakaoId){
        return participateChallengeService.getChallengeCertList(challengeId, kakaoId);
    }
}
