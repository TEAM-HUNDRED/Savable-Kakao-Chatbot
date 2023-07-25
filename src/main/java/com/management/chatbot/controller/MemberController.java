package com.management.chatbot.controller;

import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.KakaoBasicCardResponseDto;
import com.management.chatbot.service.dto.KakaoDto.BasicCard;
import com.management.chatbot.service.dto.KakaoDto.ButtonDto;
import com.management.chatbot.service.dto.KakaoRequestDto;
import com.management.chatbot.service.dto.KakaoResponseDto;
import com.management.chatbot.service.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/new-member") // 회원 가입
    public HashMap<String, Object> joinMember(@RequestBody KakaoRequestDto kakaoRequestDto) {

        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();
        String username = kakaoRequestDto.getAction().getParams().get("User_confirm");

        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .username(username)
                .kakaoId(kakaoId)
                .reward(0L)
                .savedMoney(0L)
                .build();

        memberService.save(requestDto); // 새로운 유저 저장

        HashMap<String, String > data = new HashMap<>();
        data.put("username", username);
        return new KakaoResponseDto().makeResponseDate(data);
    }

    @PostMapping("/ranking") // 랭킹 url 조회
    public HashMap<String, Object> findRankingUrl(@RequestBody KakaoRequestDto kakaoRequestDto) {
        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();

        List<ButtonDto> buttonDtoList = new ArrayList<>();
        // 기프티콘 샵 url 버튼
        ButtonDto buttonDto = ButtonDto.builder()
                .label("랭킹 보러가기")
                .action("webLink")
                .webLinkUrl("http://savable.net/ranking?kakaoId=" + kakaoId)
                .build();
        buttonDtoList.add(buttonDto);

        String message = "세이버님의 현재 랭킹이 궁금하신가요\uD83D\uDE06?\n" +
                "아래 '랭킹 보러가기' 버튼을 눌러 세이버님의 랭킹을 확인하세요!\n" +
                "\n" +
                "성실히 참여한 세이버분들께 아래와 같은 보상을 지급하고 있습니다❤\uFE0F\u200D\uD83D\uDD25\n" +
                "\uD83D\uDCCC 랭킹 보상 금액\n" +
                "▪\uFE0F 1등: 1,000원\n" +
                "▪\uFE0F 2등: 800원\n" +
                "▪\uFE0F 3등: 500원\n" +
                "▪\uFE0F 4, 5등: 300원\n" +
                "(랭킹은 매주 일요일 11:59PM에 초기화되며, 월요일에 보상이 지급됩니다.)";
        BasicCard basicCardDto = BasicCard.builder()
                .title(message)
                .thumbnail(BasicCard.Thumbnail.builder()
                        .imageUrl("https://chatbot-budket.s3.ap-northeast-2.amazonaws.com/management/ranking-chatbot-thumbnail.jpg")
                        .build())
                .buttons(buttonDtoList)
                .build();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> basicCard = new HashMap<>();
        basicCard.put("basicCard", basicCardDto);
        outputs.add(basicCard);
        return new KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }

    @PostMapping("/giftshop") // 기프티콘 샵 url 조회
    public HashMap<String, Object> findGiftShopUrl(@RequestBody KakaoRequestDto kakaoRequestDto) {

        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();

        List<ButtonDto> buttonDtoList = new ArrayList<>();
        // 기프티콘 샵 url 버튼
        ButtonDto buttonDto = ButtonDto.builder()
                .label("기프티콘 구매하기")
                .action("webLink")
                .webLinkUrl("http://savable.net/shop/" + kakaoId)
                .build();
        buttonDtoList.add(buttonDto);

        BasicCard basicCardDto = BasicCard.builder()
                .title("절약을 통해 모은 \uD83C\uDF81세이버블 포인트\uD83C\uDF81로 기프티콘을 구매하세요\uD83D\uDE0A")
                .thumbnail(BasicCard.Thumbnail.builder()
                        .imageUrl("https://chatbot-budket.s3.ap-northeast-2.amazonaws.com/management/giftshop-chatbot-thumbnail.jpg")
                        .build())
                .buttons(buttonDtoList)
                .build();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> basicCard = new HashMap<>();
        basicCard.put("basicCard", basicCardDto);
        outputs.add(basicCard);
        return new KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }
}
