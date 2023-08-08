package com.management.chatbot.controller;

import com.management.chatbot.Exception.AlreadyJoinedException;
import com.management.chatbot.Exception.DefaultException;
import com.management.chatbot.Exception.ExistMemberException;
import com.management.chatbot.Exception.MaxCertificationException;
import com.management.chatbot.service.dto.KakaoDto.KakaoResponseDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({DefaultException.class})
    @ResponseBody
    public HashMap<String, Object> handleDefaultException(DefaultException ex) {
        return new KakaoResponseDto().makeResponseBody(ex.getMessage());
    }

    @ExceptionHandler({AlreadyJoinedException.class})
    @ResponseBody
    public HashMap<String, Object> handleAlreadyJoinedException(AlreadyJoinedException ex) {
        return new KakaoResponseDto().makeResponseBody(ex.getMessage());
    }

    @ExceptionHandler({MaxCertificationException.class})
    @ResponseBody
    public HashMap<String, Object> handleAlreadyJoinedException(MaxCertificationException ex) {
        return new KakaoResponseDto().makeResponseBody(ex.getMessage());
    }

    @ExceptionHandler({ExistMemberException.class})
    @ResponseBody
    public HashMap<String, Object> handleExistMemberException(ExistMemberException ex) {
        return new KakaoResponseDto().makeResponseBody(ex.getMessage());
    }
}