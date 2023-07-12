package com.management.chatbot.controller;

import com.management.chatbot.service.dto.KakaoResponseDto;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;

@RestController
public class TestController {

    @PostMapping("/test") // json 형식을 그대로 출력하는 코드
    public HashMap<String, Object> returnJson(@RequestBody String json) {
        return new KakaoResponseDto().makeResponseBody(json + LocalDate.now(ZoneId.of("Asia/Seoul")).toString());
    }
}
