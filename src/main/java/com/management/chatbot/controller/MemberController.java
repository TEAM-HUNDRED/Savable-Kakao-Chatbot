package com.management.chatbot.controller;

import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.KakaoRequestDto;
import com.management.chatbot.service.dto.KakaoResponseDto;
import com.management.chatbot.service.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/new-member") // 회원 가입
    public HashMap<String, Object> joinMember(@RequestBody KakaoRequestDto kakaoRequestDto) {

        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();
        String username = kakaoRequestDto.getAction().getParams().get("User_confirm");

        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .username(username)
                .kakaoId(kakaoId)
                .reward(0L)
                .savedMoney(0L)
                .build();

        memberService.save(requestDto); // 새로운 유저 저장

        KakaoResponseDto kakaoResponseDto = new KakaoResponseDto();

        return new KakaoResponseDto().makeResponseBody("세이버 " + username + "님 안녕하세요 :)\rSavable에 오신 것을 환영합니다.\n\n"
                + System.lineSeparator()
                + "챌린지에 참여하고 싶다면 하단의 \"챌린지 종류\" 버튼을 눌러주세요☺️");
    }
}
