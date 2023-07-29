package com.management.chatbot.controller;

import com.management.chatbot.Exception.DefaultException;
import com.management.chatbot.domain.Member;
import com.management.chatbot.domain.Participation;
import com.management.chatbot.service.ChallengeService;
import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.*;
import com.management.chatbot.service.dto.KakaoDto.BasicCard;
import com.management.chatbot.service.dto.KakaoDto.ButtonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CertificationController {

    private final ChallengeService challengeService;
    private final MemberService memberService;

    @PostMapping("/certification/menu") // 참여중인 챌린지 목록(인증 시 사용)
    public HashMap<String, Object> certificationMenu(@RequestBody KakaoRequestDto kakaoRequestDto) {
        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();

        MemberResponseDto memberResponseDto = memberService.findByKakaoId(kakaoId);
        if (memberResponseDto.getParticipationList() == null) { // 참여중인 챌린지가 없는 경우
            throw new DefaultException( "세이버 " + memberResponseDto.getUsername() + "님은 현재 참여중인 챌린지가 없습니다.\r하단의 \"챌린지 목록\"을 누르고 \"챌린지 종류\" 버튼을 클릭해 원하는 챌린지에 신청한 후 인증해주세요😃");
        }

        List<Participation> participationList = memberResponseDto.getParticipationList();

        List<ButtonDto> buttonDtoList = new ArrayList<>();
        for (Participation participation : participationList) {
            Long challengeId = participation.getChallengeId();
            ChallengeResponseDto challengeResponseDto = challengeService.findById(challengeId);

            String challengeTitle = challengeResponseDto.getTitle();
            HashMap<String, String> extra = new HashMap<>();
            extra.put("challenge_id", String.valueOf(challengeId));
            ButtonDto buttonDto = ButtonDto.builder()
                    .label(challengeTitle)
                    .action("block")
                    .blockId("64a6659d53ad9f7b8fa9887d")
                    .extra(extra)
                    .build();
            buttonDtoList.add(buttonDto);
        }

        BasicCard basicCardDto = BasicCard.builder()
                .title("인증할 챌린지를 선택해주세요😃")
                .thumbnail(BasicCard.Thumbnail.builder()
                        .imageUrl("https://raw.githubusercontent.com/TEAM-HUNDRED/Savable-Kakao-Chatbot/6bc3a58b3f524c40a520e312e8395588e3a370e9/src/main/resources/static/images/cert-thumnail.jpg")
                        .build())
                .buttons(buttonDtoList)
                .build();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> basicCard = new HashMap<>();
        basicCard.put("basicCard", basicCardDto);
        outputs.add(basicCard);
        return new KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }

    @PostMapping("/certification/image")
    public HashMap<String, Object> certificationImage(@RequestBody KakaoImageRequestDto kakaoImageRequestDto) {
        // 인증 정보
        String certificationImage = kakaoImageRequestDto.getAction().getParams().get("Certification_image");
        String challengeId = kakaoImageRequestDto.getAction().getClientExtra().get("challenge_id");

        List<ButtonDto> buttonDtoList = new ArrayList<>();
        // 예 버튼
        HashMap<String, String> extraYes = new HashMap<>();
        extraYes.put("challenge_id", String.valueOf(challengeId));
        extraYes.put("certification_image", certificationImage);
        ButtonDto buttonDto = ButtonDto.builder()
                .label("잘 전송했어요☺️")
                .action("block")
                .blockId("64b042fa1be84973902bc014")
                .extra(extraYes)
                .build();

        // 아니오 버튼
        HashMap<String, String> extraNo = new HashMap<>();
        extraNo.put("challenge_id", String.valueOf(challengeId));
        ButtonDto buttonDto2 = ButtonDto.builder()
                .label("잘못 전송했어요😥")
                .action("block")
                .blockId("64a6659d53ad9f7b8fa9887d")
                .extra(extraNo)
                .build();

        buttonDtoList.add(buttonDto);
        buttonDtoList.add(buttonDto2);

        BasicCard basicCardDto = BasicCard.builder()
                .title("올바른 이미지를 전송했는지 확인해주세요😃")
                .thumbnail(BasicCard.Thumbnail.builder()
                        .imageUrl(certificationImage)
                        .build())
                .buttons(buttonDtoList)
                .build();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> basicCard = new HashMap<>();
        basicCard.put("basicCard", basicCardDto);
        outputs.add(basicCard);
        return new KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }

    @PostMapping("/certification/message")
    public HashMap<String, Object> certificationMessage(@RequestBody KakaoImageRequestDto kakaoRequestDto) {

        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();
        String certificationImage = kakaoRequestDto.getAction().getClientExtra().get("certification_image");
        String challengeId = kakaoRequestDto.getAction().getClientExtra().get("challenge_id");
        String message = kakaoRequestDto.getAction().getParams().get("message");

        // 챌린지 정보
        ChallengeResponseDto challengeResponseDto = challengeService.findById(Long.parseLong(challengeId));

        // 인증
        Member member = memberService.certify(kakaoId, certificationImage, message, challengeResponseDto);

        String title = member.getUsername() + " 세이버님 안녕하세요\r"
                + challengeResponseDto.getTitle() + " 인증이 완료되었습니다🎉\r\r";
        String description = "Savable과 함께 티끌 모으기!\r 앞으로도 함께 해요☺️\r\r"
                + " 하단의 '절약금액 확인하기' 버튼을 눌러 절약금액을 확인하세요😃";

        List<ButtonDto> buttonDtoList = new ArrayList<>();
        // 기프티콘 샵 url 버튼
        ButtonDto buttonDto = ButtonDto.builder()
                .label("절약금액 확인하기")
                .action("webLink")
                .webLinkUrl("http://savable.net/challenge?kakaoId=" + kakaoId)
                .build();
        buttonDtoList.add(buttonDto);

        BasicCard basicCardDto = BasicCard.builder()
                .title(title)
                .description(description)
                .thumbnail(BasicCard.Thumbnail.builder()
                        .imageUrl("https://chatbot-budket.s3.ap-northeast-2.amazonaws.com/management/challenge-complete.jpg")
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
