package com.management.chatbot.controller;

import com.management.chatbot.service.dto.KakaoDto.KakaoResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class TestController {

    @PostMapping("/test") // json 형식을 그대로 출력하는 코드
    public HashMap<String, Object> returnJson(@RequestBody String json) {
        return new KakaoResponseDto().makeResponseBody(json);
    }
}
