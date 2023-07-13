package com.management.chatbot.controller;

import com.management.chatbot.Exception.DefaultException;
import com.management.chatbot.domain.Participation;
import com.management.chatbot.service.ChallengeService;
import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.*;
import com.management.chatbot.service.dto.KakaoDto.BasicCard;
import com.management.chatbot.service.dto.KakaoDto.ButtonDto;
import com.management.chatbot.service.dto.KakaoDto.SimpleImageDto;
import com.management.chatbot.service.dto.KakaoDto.SimpleTextDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipationController {

    private final MemberService memberService;
    private final ChallengeService challengeService;

    @PostMapping("/participation") // 챌린지 참여
    public HashMap<String, Object> participateChallenge(@RequestBody KakaoRequestDto kakaoRequestDto) {
        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();// 유저의 카카오 아이디
        String challengeId = kakaoRequestDto.getAction().getClientExtra().get("Challenge_id");// 챌린지 아이디

        ParticipationSaveRequestDto participationSaveRequestDto = ParticipationSaveRequestDto.builder()
                .challengeId(Long.parseLong(challengeId))
                .certificationCnt(0L)
                .startDate(new Timestamp(System.currentTimeMillis()))
                .build();

        memberService.participate(kakaoId, participationSaveRequestDto); // 챌린지 참여
        ChallengeResponseDto challengeResponseDto = challengeService.findById(Long.parseLong(challengeId)); // 챌린지 정보

        String challengeTitle = challengeResponseDto.getTitle();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> simpleText1 = new HashMap<>();
        HashMap<String, Object> simpleText2 = new HashMap<>();
        HashMap<String, Object> simpleImage = new HashMap<>();

        // 메시지 1
        String participateText = challengeTitle + " 신청이 완료되었습니다.\n앞으로 Savable과 함께 열심히 절약해 나가요🔥";
        SimpleTextDto simpleTextDto1 = SimpleTextDto.builder()
                .text(participateText)
                .build();

        simpleText1.put("simpleText", simpleTextDto1);
        outputs.add(simpleText1);

        // 메시지 2
        SimpleImageDto simpleImageDto = SimpleImageDto.builder()
                .imageUrl(challengeResponseDto.getCertExam())
                .altText(challengeTitle + " 인증 예시 사진")
                .build();

        simpleImage.put("simpleImage", simpleImageDto);
        outputs.add(simpleImage);

        // 메시지 3
        String certExamTitle = "▶️ " + challengeTitle + " 인증 방법\n첨부된 이미지를 참고하여 인증 사진을 보내주세요.\n\n매일 최대 2회 인증할 수 있으며, 1회 인증 마다 Savable 포인트 "
                + challengeResponseDto.getReward()
                +"원을 받아가실 수 있습니다🥰\n(인증 사진 조작 시 보상 지급이 불가능하며, 패널티가 부과될 수 있습니다.)";
        SimpleTextDto simpleTextDto2 = SimpleTextDto.builder()
                .text(certExamTitle)
                .build();

        simpleText2.put("simpleText", simpleTextDto2);
        outputs.add(simpleText2);

        return new KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }

    @PostMapping("/status") // 챌린지 참여 현황
    public HashMap<String, Object> status(@RequestBody KakaoRequestDto kakaoRequestDto) {
        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId(); // 유저의 카카오 아이디
        System.out.println(kakaoId);

        MemberResponseDto memberResponseDto = memberService.findByKakaoId(kakaoId); // 유저 정보
        String message = memberResponseDto.getUsername() + " 세이버님의 현재 절약 현황입니다.\r"
                + "💸총 절약 금액: " + memberResponseDto.getSavedMoney() + "원\r"
                + "🎁총 세이버블 포인트: " + memberResponseDto.getReward() + "원";
        return new KakaoResponseDto().makeResponseBody(message);
    }
}
