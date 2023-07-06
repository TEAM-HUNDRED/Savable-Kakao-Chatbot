package com.management.chatbot.controller;

import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.KakaoRequestDto;
import com.management.chatbot.service.dto.KakaoResponseDto;
import com.management.chatbot.service.dto.ParticipationSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;

@RestController
@RequestMapping("/participation")
@RequiredArgsConstructor
public class ParticipationController {

    private final MemberService memberService;

    @PostMapping("/save") // 챌린지 참여
    public HashMap<String, Object> participateChallenge(@RequestBody KakaoRequestDto kakaoRequestDto) {
        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();// 유저의 카카오 아이디
        String challengeId = kakaoRequestDto.getAction().getParams().get("Challenge_id");// 챌린지 아이디
        ParticipationSaveRequestDto participationSaveRequestDto = ParticipationSaveRequestDto.builder()
                .challengeId(Long.parseLong(challengeId))
                .certificationCnt(0L)
                .startDate(new Timestamp(System.currentTimeMillis()))
                .build();

        memberService.participate(kakaoId, participationSaveRequestDto); // 챌린지 참여

        return new KakaoResponseDto().makeResponseBody("챌린지 신청이 완료되었습니다.\n" +
                "앞으로 Savable과 함께 열심히 절약해 나가요🔥");
    }

}
