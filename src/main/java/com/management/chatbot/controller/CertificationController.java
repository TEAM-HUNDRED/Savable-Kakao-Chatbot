package com.management.chatbot.controller;

import com.management.chatbot.service.ChallengeService;
import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.*;
import com.management.chatbot.service.dto.KakaoDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
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

        List<ParticipationSaveRequestDto> participationList = memberService.findParticipatingChallenges(kakaoId);

        System.out.println(participationList);
        if(participationList.isEmpty()) {
            return new KakaoResponseDto().makeResponseBody("세이버님께서 참여하신 챌린지가 성공적으로 종료되었습니다\uD83D\uDE03\n" +
                    "\n" +
                    "만약 챌린지에 다시 참여를 원하시면, 하단의 \"챌린지 목록\" 클릭 → \"챌린지 종류\"를 클릭하여 원하시는 챌린지에 다시 신청해주세요!\n" +
                    "\n" +
                    "Savable과 함께 지출을 절약하여 부유한 삶을 누려봐요\uD83E\uDD73");
        }

        List<ButtonDto> buttonDtoList = new ArrayList<>();
        for (ParticipationSaveRequestDto participation : participationList) {
            Long challengeId = participation.getChallengeId();
            ChallengeResponseDto challengeResponseDto = challengeService.findById(challengeId);

            String challengeTitle = challengeResponseDto.getTitle().replace(" 절약 챌린지", "");
            HashMap<String, String> extra = new HashMap<>();
            extra.put("challenge_id", String.valueOf(challengeId));

            SimpleDateFormat sdf = new SimpleDateFormat("M/d");
            String formattedDate = sdf.format(participation.getEndDate());

            ButtonDto buttonDto = ButtonDto.builder()
                    .label(challengeTitle + "(~" + formattedDate + ")")
                    .action("block")
                    .blockId("64a6659d53ad9f7b8fa9887d")
                    .extra(extra)
                    .build();
            buttonDtoList.add(buttonDto);
        }

        BasicCard basicCardDto = BasicCard.builder()
                .title("인증할 챌린지를 선택해주세요😃")
                .thumbnail(BasicCard.Thumbnail.builder()
                        .imageUrl("https://chatbot-budket.s3.ap-northeast-2.amazonaws.com/management/cert-thumnail.jpg")
                        .build())
                .buttons(buttonDtoList)
                .build();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> basicCard = new HashMap<>();
        basicCard.put("basicCard", basicCardDto);
        outputs.add(basicCard);
        return new com.management.chatbot.service.dto.KakaoDto.KakaoResponseDto.KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }

    @PostMapping("/certification/image")
    public HashMap<String, Object> certificationImage(@RequestBody KakaoRequestDto kakaoRequestDto) {
        // 인증 정보
        String certificationImage = kakaoRequestDto.getAction().getParams().get("Certification_image");
        String challengeId = kakaoRequestDto.getAction().getClientExtra().get("challenge_id");

        List<ButtonDto> buttonDtoList = new ArrayList<>();
        // 예 버튼
        HashMap<String, String> extraYes = new HashMap<>();
        extraYes.put("challenge_id", String.valueOf(challengeId));
        extraYes.put("certification_image", certificationImage);
        ButtonDto buttonDto = ButtonDto.builder()
                .label("O")
                .action("block")
                .blockId("64b042fa1be84973902bc014")
                .extra(extraYes)
                .build();

        // 아니오 버튼
        HashMap<String, String> extraNo = new HashMap<>();
        extraNo.put("challenge_id", String.valueOf(challengeId));
        ButtonDto buttonDto2 = ButtonDto.builder()
                .label("X")
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
                        .fixedRatio(true)
                        .build())
                .buttons(buttonDtoList)
                .build();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> basicCard = new HashMap<>();
        basicCard.put("basicCard", basicCardDto);
        outputs.add(basicCard);
        return new com.management.chatbot.service.dto.KakaoDto.KakaoResponseDto.KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }

    @PostMapping("/certification/message")
    public HashMap<String, Object> certificationMessage(@RequestBody KakaoRequestDto kakaoRequestDto) {

        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();
        String certificationImage = kakaoRequestDto.getAction().getClientExtra().get("certification_image");
        String challengeId = kakaoRequestDto.getAction().getClientExtra().get("challenge_id");
        String message = kakaoRequestDto.getAction().getParams().get("message");

        MemberResponseDto member = memberService.findByKakaoId(kakaoId);
        ChallengeResponseDto challengeResponseDto = challengeService.findById(Long.parseLong(challengeId));

        // 인증
        ParticipationSaveRequestDto participationSaveRequestDto = memberService.certify(kakaoId, certificationImage, message, challengeResponseDto);

        //메시지 1
        Long certificationCnt = participationSaveRequestDto.getCertificationCnt();
        Long goalCnt = participationSaveRequestDto.getGoalCnt();
        String simpleTextMessage =  challengeResponseDto.getTitle() + " 인증이 완료되었습니다\uD83C\uDF89\n\n" +
                "🔥세이버 " + member.getUsername()  +"님의 챌린지 현황🔥\n" +
                "- 총 인증 횟수: " + certificationCnt + "회\n" +
                "- 목표 인증 횟수: " + goalCnt + "회\n" +
                "——————————————\n";

        if (certificationCnt < goalCnt) {
            simpleTextMessage += challengeResponseDto.getTitle() + " 성공을 위해 " +
                    "앞으로 ❗️" + (goalCnt - certificationCnt) +
                    "번❗️ 더 절약해야 해요\uD83D\uDE24\n" +
                    "부자되는 그 날까지 파이팅 \uD83D\uDCB8\uD83E\uDD0D";
        } else if (certificationCnt == goalCnt) {
            simpleTextMessage += challengeResponseDto.getTitle() + " 성공을 축하합니다👏🏻👏🏻\n\n" +
                    "절약을 위한 노력으로 총 " +
                    challengeResponseDto.getSavedMoney() * goalCnt + "원을 아낄 수 있었어요!\n" +
                    "부자에 한 발짝 가까워진 거 같지 않나요..?\uD83D\uDE01\n" +
                    "\n" +
                    "지금까지의 노력은 더 나은 미래를 위한 중요한 시작입니다!\n" +
                    "앞으로도 지속적인 절약을 통해 더 큰 성취를 이루시길 바라요\uD83E\uDD70\n\n" +
                    "(챌린지 기간이 끝날 때까지 계속 인증할 수 있습니다)";
        } else {
            simpleTextMessage += "WOW!\n인증 횟수가 목표 횟수를 뛰어 넘었어요!\n" +
                    "세이버님의 놀라운 노력에 박수를 보냅니다\uD83D\uDC4F\uD83C\uDFFB\uD83D\uDC4F\uD83C\uDFFB\n\n" +
                    "자금을 적절히 관리하여 목표를 향해 꾸준히 나아가세요!";
        }

        SimpleTextDto simpleTextDto = SimpleTextDto.builder()
                .text(simpleTextMessage)
                .build();

        // 메시지 2
        String title = "하단의 '절약금액 확인하기' 버튼을 눌러 절약금액을 확인하세요😃";

        List<ButtonDto> buttonDtoList = new ArrayList<>();
        // 절약 현황 url 버튼
        ButtonDto buttonDto = ButtonDto.builder()
                .label("절약금액 확인하기")
                .action("webLink")
                .webLinkUrl("http://savable.net/challenge?kakaoId=" + kakaoId)
                .build();
        buttonDtoList.add(buttonDto);

        BasicCard basicCardDto = BasicCard.builder()
                .title(title)
                .thumbnail(BasicCard.Thumbnail.builder()
                        .imageUrl("https://chatbot-budket.s3.ap-northeast-2.amazonaws.com/management/challenge-complete.jpg")
                        .build())
                .buttons(buttonDtoList)
                .build();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> simpleText = new HashMap<>();
        HashMap<String, Object> basicCard = new HashMap<>();

        simpleText.put("simpleText", simpleTextDto);
        outputs.add(simpleText);

        basicCard.put("basicCard", basicCardDto);
        outputs.add(basicCard);

        return new com.management.chatbot.service.dto.KakaoDto.KakaoResponseDto.KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }
}