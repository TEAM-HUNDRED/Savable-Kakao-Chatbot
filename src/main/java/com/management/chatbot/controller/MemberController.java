package com.management.chatbot.controller;

import com.management.chatbot.service.dto.KakaoRequestDto;
import com.management.chatbot.domain.CertInfo;
import com.management.chatbot.domain.Certification;
import com.management.chatbot.domain.Member;
import com.management.chatbot.domain.Participation;
import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.KakaoResponseDto;
import com.management.chatbot.service.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/new-member") // 회원 가입
    public HashMap<String, Object> joinMember(@RequestBody KakaoRequestDto kakaoRequestDto) {

        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();
        String name = kakaoRequestDto.getAction().getParams().get("User_confirm");

        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .name(name)
                .kakaoId(kakaoId)
                .reward(0L)
                .savedMoney(0L)
                .build();

        memberService.save(requestDto); // 새로운 유저 저장

        KakaoResponseDto kakaoResponseDto = new KakaoResponseDto();

        return new KakaoResponseDto().makeResponseBody(name + "님 안녕하세요 :)\rSavable에 오신 것을 환영합니다."
                + System.lineSeparator()
                + "챌린지에 참여하고 싶다면 하단의 \"챌린지 종류\" 버튼을 눌러 챌린지 종류를 확인하고 \"신청하기\" 버튼을 클릭해주세요☺️");
    }
}
