package com.management.chatbot.controller;

import com.management.chatbot.service.ChallengeService;
import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.*;
import com.management.chatbot.service.dto.KakaoDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipationController {

    private final MemberService memberService;
    private final ChallengeService challengeService;

    @PostMapping("/challenge/menu")
    public HashMap<String, Object> challengeList(@RequestBody KakaoRequestDto kakaoRequestDto){

        MemberResponseDto memberResponseDto = memberService.findByKakaoId(kakaoRequestDto.getUserRequest().getUser().getId());
        Timestamp timestamp = memberResponseDto.getCreatedAt();

        List<BasicCard> basicCardList = new ArrayList<>();
        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> carousel = new HashMap<>();

        LocalDate targetDate = LocalDate.of(2023, 7, 25);

        // 가입 날짜 비교
        if (timestamp.toLocalDateTime().toLocalDate().isBefore(targetDate)) {
            // timestamp의 날짜가 7월 24일인 경우
            basicCardList.add(makeItem(1L));
            basicCardList.add(makeItem(2L));
        } else {
            // timestamp의 날짜가 7월 24일 이후인 경우
            basicCardList.add(makeItem(3L));
            basicCardList.add(makeItem(4L));
        }

        CarouselDto carouselDto = CarouselDto.builder()
                .items(basicCardList)
                .build();

        carousel.put("carousel", carouselDto);
        outputs.add(carousel);
        return new KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }

    public BasicCard makeItem(Long challengeId) {
        List<String> descriptionsUrl = new ArrayList<>();
        List<ButtonDto> buttonDtoList = new ArrayList<>();

        // url 직접 등록
        descriptionsUrl.add("https://superb-nannyberry-327.notion.site/1-ba36b6b224834ac3959a793f3fb8d550");
        descriptionsUrl.add("https://superb-nannyberry-327.notion.site/1-0a178984c9294df0bf766a87332f8847");
        descriptionsUrl.add("https://superb-nannyberry-327.notion.site/2-01fa2011d24d4e2b92997d0c0f5b3f6c");
        descriptionsUrl.add("https://superb-nannyberry-327.notion.site/2-f671daaa7e6d49a2b1cc08f864178f7d?pvs=4");

        ChallengeResponseDto challengeResponseDto = challengeService.findById(challengeId);

        ButtonDto buttonDto = ButtonDto.builder()
                .label("자세히 보기")
                .action("webLink")
                .webLinkUrl(descriptionsUrl.get(challengeId.intValue()-1))
                .build();

        // 신청하기 버튼
        HashMap<String, String> extra = new HashMap<>();
        extra.put("Challenge_id", String.valueOf(challengeId));
        ButtonDto buttonDto2 = ButtonDto.builder()
                .label("신청하기")
                .action("block")
                .blockId("649c7242acaa9c34a7564e2f")
                .extra(extra)
                .build();

        buttonDtoList.add(buttonDto);
        buttonDtoList.add(buttonDto2);

        BasicCard basicCardDto = new BasicCard();
        if (challengeResponseDto.getTitle().equals("음료값 절약 챌린지")){
            basicCardDto = BasicCard.builder()
                    .title("음료값 절약 챌린지☕️")
                    .description("음료값 절약하고 기프티콘 받아가자")
                    .thumbnail(BasicCard.Thumbnail.builder()
                            .imageUrl(challengeResponseDto.getThumbnail())
                            .fixedRatio(true)
                            .build())
                    .buttons(buttonDtoList)
                    .buttonLayout("horizontal")
                    .build();
        } else {
            basicCardDto = BasicCard.builder()
                    .title("배달비 절약 챌린지🍔️")
                    .description("배달비 절약하고 기프티콘 받아가자")
                    .thumbnail(BasicCard.Thumbnail.builder()
                            .imageUrl(challengeResponseDto.getThumbnail())
                            .fixedRatio(true)
                            .build())
                    .buttons(buttonDtoList)
                    .buttonLayout("horizontal")
                    .build();
        }

        return basicCardDto;
    }

    @PostMapping("/participation") // 챌린지 참여
    public HashMap<String, Object> participateChallenge(@RequestBody KakaoRequestDto kakaoRequestDto) {
        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();// 유저의 카카오 아이디
        String challengeId = kakaoRequestDto.getAction().getClientExtra().get("Challenge_id");// 챌린지 아이디

        ParticipationSaveRequestDto participationSaveRequestDto = ParticipationSaveRequestDto.builder()
                .challengeId(Long.parseLong(challengeId))
                .certificationCnt(0L)
                .startDate(new Timestamp(System.currentTimeMillis()))
                .build();

        memberService.participate(kakaoId, participationSaveRequestDto); // 챌린지 참여
        ChallengeResponseDto challengeResponseDto = challengeService.findById(Long.parseLong(challengeId)); // 챌린지 정보

        String challengeTitle = challengeResponseDto.getTitle();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> simpleText1 = new HashMap<>();
        HashMap<String, Object> simpleText2 = new HashMap<>();
        HashMap<String, Object> simpleImage = new HashMap<>();

        // 메시지 1
        String participateText = challengeTitle + " 신청이 완료되었습니다.\n앞으로 Savable과 함께 열심히 절약해 나가요🔥";
        SimpleTextDto simpleTextDto1 = SimpleTextDto.builder()
                .text(participateText)
                .build();

        simpleText1.put("simpleText", simpleTextDto1);
        outputs.add(simpleText1);

        // 메시지 2
        SimpleImageDto simpleImageDto = SimpleImageDto.builder()
                .imageUrl(challengeResponseDto.getCertExam())
                .altText(challengeTitle + " 인증 예시 사진")
                .build();

        simpleImage.put("simpleImage", simpleImageDto);
        outputs.add(simpleImage);

        // 메시지 3
        String certExamTitle = "▶️ " + challengeTitle + " 인증 방법\n첨부된 이미지를 참고하여 인증 사진을 보내주세요.\n\n매일 최대 2회 인증할 수 있으며, 1회 인증 마다 Savable 포인트 "
                + challengeResponseDto.getReward()
                +"원을 받아가실 수 있습니다🥰\nSavable 포인트를 이용해 추후 기프티콘 구매가 가능합니다.";
        SimpleTextDto simpleTextDto2 = SimpleTextDto.builder()
                .text(certExamTitle)
                .build();

        simpleText2.put("simpleText", simpleTextDto2);
        outputs.add(simpleText2);

        return new KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }

    @PostMapping("/status") // 챌린지 참여 현황
    public HashMap<String, Object> status(@RequestBody KakaoRequestDto kakaoRequestDto) {
        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId(); // 유저의 카카오 아이디
        System.out.println(kakaoId);

        MemberResponseDto memberResponseDto = memberService.findByKakaoId(kakaoId); // 유저 정보
        String message = "세이버 " + memberResponseDto.getUsername() + "님의 현재 절약 현황입니다.\r"
                + "💸총 절약 금액: " + memberResponseDto.getSavedMoney() + "원\r"
                + "🎁총 세이버블 포인트: " + memberResponseDto.getReward() + "원";
        return new KakaoResponseDto().makeResponseBody(message);
    }
}
