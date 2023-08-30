package com.management.web.controller;


import com.management.web.service.MainService;
import com.management.web.service.dto.MyMainInfoDto;
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
