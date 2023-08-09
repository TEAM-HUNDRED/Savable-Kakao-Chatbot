package com.management.web.controller;

import com.management.web.service.GiftcardHistoryService;
import com.management.web.service.dto.GiftcardHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GiftcardHistoryController {
    private final GiftcardHistoryService giftcardHistoryService;

    @GetMapping("/gift-histories/{kakaoId}")
    public List<GiftcardHistoryDto> searchGiftcardHistoryList(@PathVariable("kakaoId") String kakaoId){
        return giftcardHistoryService.findByKakaoId(kakaoId);
    }
}
