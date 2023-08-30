package com.management.web.controller;

import com.management.chatbot.service.MemberService;
import com.management.web.domain.GiftcardOrder;
import com.management.web.service.GiftcardHistoryService;
import com.management.web.service.GiftcardService;
import com.management.web.service.OrderService;
import com.management.web.service.dto.GiftcardHistoryDto;
import com.management.web.service.dto.GiftcardMemberDto;
import com.management.web.service.dto.GiftcardResponseDto;
import com.management.web.service.dto.OrderSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GiftcardController {

    private final GiftcardService giftcardService;
    private final MemberService memberService;
    private final OrderService orderService;
    private final GiftcardHistoryService giftcardHistoryService;

    @GetMapping("/shop/{kakaoId}")
    public ResponseEntity<HashMap<String, Object>> giftcardList(@PathVariable("kakaoId") String kakaoId) {
        GiftcardMemberDto member = new GiftcardMemberDto(memberService.findByKakaoId(kakaoId));

        HashMap<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        List<GiftcardResponseDto> giftcardDtoList = giftcardService.findBySaleYNOrderByPriceAsc(true);

        HashMap<Long, List<GiftcardResponseDto>> giftcards = new HashMap<>();
        for (GiftcardResponseDto giftcardDto : giftcardDtoList) {
            Long price = giftcardDto.getPrice() / 1000 * 1000;
            if (!giftcards.containsKey(price)) {
                giftcards.put(price, new ArrayList<>());
            }
            giftcards.get(price).add(giftcardDto);
        }

        response.put("member", member);
        response.put("giftcards", giftcards);

        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/order/{giftcardId}/{kakaoId}")
    public ResponseEntity<HashMap<String, Object>> orderDetails(@PathVariable("giftcardId") Long giftcardId, @PathVariable("kakaoId") String kakaoId) {
        GiftcardMemberDto member = new GiftcardMemberDto(memberService.findByKakaoId(kakaoId));
        GiftcardResponseDto giftcard = giftcardService.findById(giftcardId);

        HashMap<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        response.put("member", member);
        response.put("giftcard", giftcardService.findById(giftcardId));

        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/order")
    public ResponseEntity<?> orderAdd(@RequestBody OrderSaveRequestDto orderSaveRequestDto) {

        Long price = giftcardService.findById(orderSaveRequestDto.getGiftcardId()).getPrice();

        GiftcardOrder giftcardOrder = orderService.addOrder(orderSaveRequestDto, price);

        return ResponseEntity.ok(giftcardOrder);
    }

    @GetMapping("/gift-histories/{kakaoId}")
    public List<GiftcardHistoryDto> searchGiftcardHistoryList(@PathVariable("kakaoId") String kakaoId){
        return giftcardHistoryService.findByKakaoId(kakaoId);
    }
}
