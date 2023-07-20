package com.management.chatbot.web.controller;

import com.management.chatbot.web.dto.MyMainInfoDto;
import com.management.chatbot.web.service.MainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class MainController {
    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping
    public MyMainInfoDto getMainInfo(@RequestParam String kakaoId){
        return mainService.getMainInfo(kakaoId);
    }
}
