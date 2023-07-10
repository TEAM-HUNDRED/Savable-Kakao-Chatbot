package com.management.chatbot.controller;

import com.management.chatbot.domain.Member;
import com.management.chatbot.service.ChallengeService;
import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.ChallengeResponseDto;
import com.management.chatbot.service.dto.KakaoImageRequestDto;
import com.management.chatbot.service.dto.KakaoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class CertificationController {

    private final ChallengeService challengeService;
    private final MemberService memberService;

    @PostMapping("/certification")
    public HashMap<String, Object> certification(@RequestBody KakaoImageRequestDto kakaoImageRequestDto) {
        // 인증 정보
        String kakaoId = kakaoImageRequestDto.getUserRequest().getUser().getId();
        String certificationImage = kakaoImageRequestDto.getAction().getParams().get("Certification_image");
        String challengeTitle = kakaoImageRequestDto.getUserRequest().getUtterance();

        // 챌린지 정보
        ChallengeResponseDto challengeResponseDto = challengeService.findByTitle(challengeTitle);

        // 인증
        Member member = memberService.certify(kakaoId, certificationImage, challengeResponseDto);

        String message = member.getUsername() + " 세이버님 안녕하세요\r"
                + challengeResponseDto.getTitle() + " 인증이 완료되었습니다🎉\r\r"
                + "💸총 절약 금액: " + member.getSavedMoney() + "원(+" + challengeResponseDto.getSavedMoney() + "원)\r"
                + "🎁총 세이버블 포인트: " + member.getReward() + "원(+" + challengeResponseDto.getReward() + "원)\r\r"
                + "Savable과 함께 티끌 모으기! 앞으로도 함께 해요☺️\r\r"
                + "(사진 조작 적발 시 인증이 반려될 수 있으며, 추후 패널티가 부과될 예정입니다.)";
        return new KakaoResponseDto().makeResponseBody(message);
    }
}
