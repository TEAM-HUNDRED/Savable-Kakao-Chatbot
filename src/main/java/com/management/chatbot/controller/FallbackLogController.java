package com.management.chatbot.controller;

import com.management.chatbot.service.FallbackLogService;
import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.FallbackLogSaveRequestDto;
import com.management.chatbot.service.dto.KakaoRequestDto;
import com.management.chatbot.service.dto.KakaoResponseDto;
import com.management.chatbot.service.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class FallbackLogController {

    private final FallbackLogService fallbackLogService;
    private final MemberService memberService;

    @PostMapping("/fallback")
    public HashMap<String, Object> fallback(@RequestBody KakaoRequestDto kakaoRequestDto) {
        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();
        String message = kakaoRequestDto.getUserRequest().getUtterance();
        MemberResponseDto memberResponseDto = memberService.findByKakaoId(kakaoId);

        FallbackLogSaveRequestDto fallbackLogSaveRequestDto = FallbackLogSaveRequestDto.builder()
                .username(memberResponseDto.getUsername())
                .kakaoName(memberResponseDto.getKakaoName())
                .message(message)
                .sentTime(new Timestamp(System.currentTimeMillis()))
                .build();

        fallbackLogService.save(fallbackLogSaveRequestDto);
        return new KakaoResponseDto().makeResponseBody("제가 이해할 수 없는 말이에요\uD83E\uDD16\n" +
                "챗봇 사용법이 궁금하다면 채팅창에 \"챗봇 사용법\"을 입력해주세요✨\n" +
                "\n" +
                "▶\uFE0F 상담직원의 도움이 필요하다면, 좌측 하단 '+' 버튼을 누르고 '상담직원 연결' 버튼을 눌러 문의 사항을 입력해주세요⚡\uFE0F");
    }
}
