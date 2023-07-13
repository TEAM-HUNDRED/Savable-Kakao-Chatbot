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

import javax.xml.transform.sax.SAXResult;
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
        return new KakaoResponseDto().makeResoonseDate();
    }
}
