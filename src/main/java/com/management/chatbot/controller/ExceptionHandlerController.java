package com.management.chatbot.controller;

import com.management.chatbot.Exception.AlreadyJoinedException;
import com.management.chatbot.service.dto.KakaoResponseDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(AlreadyJoinedException.class)
    @ResponseBody
    public HashMap<String, Object> handleAlreadyJoinedException(AlreadyJoinedException ex) {
        return new KakaoResponseDto().makeResponseBody(ex.getMessage());
    }
}
