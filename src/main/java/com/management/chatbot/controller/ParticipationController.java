package com.management.chatbot.controller;

import com.management.chatbot.domain.Certification;
import com.management.chatbot.domain.Participation;
import com.management.chatbot.service.ChallengeService;
import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.*;
import jakarta.persistence.Basic;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipationController {

    private final MemberService memberService;
    private final ChallengeService challengeService;

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
        SimpleTextDto simpleTextDto = new SimpleTextDto().builder().text(challengeTitle
                + " 신청이 완료되었습니다.\n앞으로 Savable과 함께 열심히 절약해 나가요🔥").build();

        String certExamTitle = "▶️ " + challengeTitle
                + " 인증 방법\r첨부된 이미지를 참고하여 매일 최대 2회 인증 사진을 보내주세요.\n1회 인증 마다 Savable 포인트 "
                + challengeResponseDto.getReward()
                +"원을 받아가실 수 있습니다🥰\n(인증 사진 조작 시 보상 지급이 불가능합니다)";
        BasicCard basicCardDto = BasicCard.builder()
                .title(certExamTitle)
                .thumbnail(BasicCard.Thumbnail.builder()
                        .imageUrl(challengeResponseDto.getCertExam())
                        .build())
                .build();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> simpleText = new HashMap<>();
        HashMap<String, Object> basicCard = new HashMap<>();
        simpleText.put("simpleText", simpleTextDto);
        basicCard.put("basicCard", basicCardDto);
        outputs.add(simpleText);

        return new KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }

    @PostMapping("/participation/menu") // 참여중인 챌린지 목록(인증 시 사용)
    public HashMap<String, Object> participateChallengeTest(@RequestBody KakaoRequestDto kakaoRequestDto) {
        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();

        MemberResponseDto memberResponseDto = memberService.findByKakaoId(kakaoId);
        List<Participation> participationList = memberResponseDto.getParticipationList();

        List<ButtonDto> buttonDtoList = new ArrayList<>();
        for (Participation participation : participationList) {
            Long challengeId = participation.getChallengeId();
            ChallengeResponseDto challengeResponseDto = challengeService.findById(challengeId);

            String challengeTitle= challengeResponseDto.getTitle();
            ButtonDto buttonDto = ButtonDto.builder()
                    .label(challengeTitle)
                    .action("message")
                    .messageText(challengeTitle)
                    .build();
            buttonDtoList.add(buttonDto);
        }

        BasicCard basicCardDto = BasicCard.builder()
                .title("인증할 챌린지를 선택해주세요😃")
                .thumbnail(BasicCard.Thumbnail.builder()
                        .imageUrl("https://raw.githubusercontent.com/TEAM-HUNDRED/Savable-Kakao-Chatbot/3d99c8f3de5e52be04d6790977698aa1be819270/src/main/resources/static/images/challenge-thumbnail.jpg")
                        .build())
                .buttons(buttonDtoList)
                .build();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> basicCard = new HashMap<>();
        basicCard.put("basicCard", basicCardDto);
        outputs.add(basicCard);
        return new KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }

    @PostMapping("/status") // 챌린지 참여 현황
    public HashMap<String, Object> status(@RequestBody KakaoRequestDto kakaoRequestDto) {
        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId(); // 유저의 카카오 아이디
        System.out.println(kakaoId);

        MemberResponseDto memberResponseDto = memberService.findByKakaoId(kakaoId); // 유저 정보
        String message = memberResponseDto.getName() + " 세이버님의 현재 절약 현황입니다.\r"
                + "💸총 절약 금액: " + memberResponseDto.getSavedMoney() + "원\r"
                + "🎁총 세이버블 포인트: " + memberResponseDto.getReward() + "원";
        return new KakaoResponseDto().makeResponseBody(message);
    }
}
