package com.management.chatbot.controller;

import com.management.chatbot.domain.CheckStatus;
import com.management.chatbot.service.ChallengeService;
import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.ChallengeResponseDto;
import com.management.chatbot.service.dto.KakaoDto.*;
import com.management.chatbot.service.dto.MemberResponseDto;
import com.management.chatbot.service.dto.ParticipationSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            basicCardList.add(makeBasicCard(5L));
            basicCardList.add(makeBasicCard(1L));
            basicCardList.add(makeBasicCard(2L));
        } else {
            // timestamp의 날짜가 7월 24일 이후인 경우
            basicCardList.add(makeBasicCard(6L));
            basicCardList.add(makeBasicCard(3L));
            basicCardList.add(makeBasicCard(4L));
        }

        CarouselDto carouselDto = CarouselDto.builder()
                .items(basicCardList)
                .build();

        carousel.put("carousel", carouselDto);
        outputs.add(carousel);
        return new KakaoResponseDto.KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }

    public BasicCard makeBasicCard(Long challengeId) {// 챌린지별 basic 카드 생성
        List<String> descriptionsUrl = new ArrayList<>();
        List<ButtonDto> buttonDtoList = new ArrayList<>();

        // url 직접 등록
        descriptionsUrl.add("https://superb-nannyberry-327.notion.site/bc9163fda3ee4e21a756c23d76881b2c?pvs=4"); // 커피값 절약 챌린지(리워드 100원)
        descriptionsUrl.add("https://superb-nannyberry-327.notion.site/d54921010cce48bf941297677a741a13?pvs=4"); // 배달비 절약 챌린지(리워드 100원)
        descriptionsUrl.add("https://superb-nannyberry-327.notion.site/f67baca06ce74c3d9747c2ae1e05d179?pvs=4"); // 커피값 절약 챌린지(리워드 50원)
        descriptionsUrl.add("https://superb-nannyberry-327.notion.site/d1e8876550544ef891d5f20a42269fd6?pvs=4"); // 배달비 절약 챌린지(리워드 50원)
        descriptionsUrl.add("https://superb-nannyberry-327.notion.site/9d6d9a85080849b185e638f2a48d624f?pvs=4"); // 집밥 먹기 절약 챌린지(리워드 100원)
        descriptionsUrl.add("https://superb-nannyberry-327.notion.site/160767954d144970a912c45507908649?pvs=4"); // 집밥 먹기 절약 챌린지(리워드 50원)

        ChallengeResponseDto challengeResponseDto = challengeService.findById(challengeId);

        ButtonDto buttonDto = ButtonDto.builder()
                .label("자세히 보기")
                .action("webLink")
                .webLinkUrl(descriptionsUrl.get(challengeId.intValue()-1))
                .build();

        // 신청하기 버튼
        HashMap<String, String> extra = new HashMap<>();
        extra.put("challenge_id", String.valueOf(challengeId));
        ButtonDto buttonDto2 = ButtonDto.builder()
                .label("신청하기")
                .action("block")
                .blockId("64c56f744bc96323949e44f1")
                .extra(extra)
                .build();

        buttonDtoList.add(buttonDto);
        buttonDtoList.add(buttonDto2);

        BasicCard basicCardDto = new BasicCard();
        if (challengeResponseDto.getTitle().equals("음료값 절약 챌린지")){
            basicCardDto = BasicCard.builder()
                    .title("음료값 절약 챌린지☕️")
                    .description("음료값 절약하고 티끌 모아 부자되자💸\n" +
                            "(챌린지 진행 기간: 7일)")
                    .thumbnail(BasicCard.Thumbnail.builder()
                            .imageUrl(challengeResponseDto.getThumbnail())
                            .fixedRatio(true)
                            .build())
                    .buttons(buttonDtoList)
                    .buttonLayout("horizontal")
                    .build();
        } else if (challengeResponseDto.getTitle().equals("집밥 먹기 절약 챌린지")) {
            basicCardDto = BasicCard.builder()
                    .title("[NEW] 집밥 먹기 절약 챌린지🍚")
                    .description("식비 절약하고 티끌 모아 태산 만들자🍀\n" +
                            "(챌린지 진행 기간: 7일)")
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
                    .description("배달비 절약해서 합리적인 소비하자🙆‍♀️\n" +
                            "(챌린지 진행 기간: 7일)")
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
        String challengeId = kakaoRequestDto.getAction().getClientExtra().get("challenge_id");// 챌린지 아이디
        ChallengeResponseDto challengeResponseDto = challengeService.findById(Long.parseLong(challengeId)); // 챌린지 정보

        String goalCnt = kakaoRequestDto.getAction().getDetailParams().get("min_goal").getOrigin(); // 최소 인증 목표 횟수
        if (!isNaturalNumber(goalCnt)){
            return new KakaoResponseDto().makeResponseBody("\'절약 인증 횟수\'는 1 이상의 숫자여야 합니다.\n" +
                    "챗봇의 안내에 따라 올바른 값을 입력해주세요😢\n\n" +
                    "챌린지를 처음부터 다시 신청해주시길 바랍니다.");
        }
        Timestamp endDate = calculateEndDate(challengeResponseDto.getDuration()); // 챌린지 종료일 계산

        ParticipationSaveRequestDto participationSaveRequestDto = ParticipationSaveRequestDto.builder()
                .challengeId(Long.parseLong(challengeId))
                .certificationCnt(0L)
                .startDate(new Timestamp(System.currentTimeMillis()))
                .endDate(endDate)
                .goalCnt(Long.parseLong(goalCnt))
                .isSuccess(CheckStatus.FAIL)
                .build();

        memberService.participate(kakaoId, participationSaveRequestDto); // 챌린지 참여

        String challengeTitle = challengeResponseDto.getTitle();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> simpleText1 = new HashMap<>();
        HashMap<String, Object> simpleText2 = new HashMap<>();
        HashMap<String, Object> simpleImage = new HashMap<>();

        // 메시지 1
        SimpleDateFormat sdf = new SimpleDateFormat("M/d");
        String formattedDate = sdf.format(endDate);

        String participateText = challengeTitle + " 신청이 완료되었습니다.\n" +
                "7일 동안(" + formattedDate + "까지)" +
                " 최소 " + goalCnt + "회 이상 인증할 경우 🎉절약 챌린지 성공🎉으로 인정됩니다!\n\n" +
                "앞으로 Savable과 함께 열심히 절약해 나가요🔥";
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
        String certExamTitle = "💌" + challengeTitle + " 인증 방법💌\n" +
                "위 이미지를 참고해 인증 사진을 보내주세요.\n\n최대 인증 횟수는 제한이 없으며, 1회 인증 마다 Savable 포인트 "+
                challengeResponseDto.getReward()+
                "원을 드립니다🥰\n\n" +
                "Savable 포인트로 추후 기프티콘 구매가 가능합니다.";
        SimpleTextDto simpleTextDto2 = SimpleTextDto.builder()
                .text(certExamTitle)
                .build();

        simpleText2.put("simpleText", simpleTextDto2);
        outputs.add(simpleText2);

        return new KakaoResponseDto.KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }

    // endDate 계산해주는 함수
    public Timestamp calculateEndDate(Long duration){
        LocalDateTime currentLocalDateTime = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(59);
        LocalDateTime newLocalDateTime = currentLocalDateTime.plusDays(duration);
        return Timestamp.valueOf(newLocalDateTime);
    }

    // 자연수인지 확인하는 함수(절약 횟수 확인용)
    public static boolean isNaturalNumber(String input) {
        // Define the regex pattern to match a positive natural number
        Pattern pattern = Pattern.compile("^[1-9]\\d*$");

        // Create a Matcher object to apply the pattern to the input string
        Matcher matcher = pattern.matcher(input);

        // Check if the input string matches the pattern
        return matcher.matches();
    }

    @PostMapping("/status") // 챌린지 참여 현황
    public HashMap<String, Object> status(@RequestBody KakaoRequestDto kakaoRequestDto) {
        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();
        memberService.findByKakaoId(kakaoId); // 해당 멤버가 존재하는지 확인

        List<ButtonDto> buttonDtoList = new ArrayList<>();
        // 기프티콘 샵 url 버튼
        ButtonDto buttonDto = ButtonDto.builder()
                .label("챌린지 현황 보기")
                .action("webLink")
                .webLinkUrl("http://savable.net/challenge?kakaoId=" + kakaoId)
                .build();
        buttonDtoList.add(buttonDto);

        BasicCard basicCardDto = BasicCard.builder()
                .title("하단 버튼 클릭을 통해 세이버님의 챌린지 현황을 확인해보세요👀")
                .thumbnail(BasicCard.Thumbnail.builder()
                        .imageUrl("https://chatbot-budket.s3.ap-northeast-2.amazonaws.com/management/challenge-status-thumbnail.jpg")
                        .build())
                .buttons(buttonDtoList)
                .build();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> basicCard = new HashMap<>();
        basicCard.put("basicCard", basicCardDto);
        outputs.add(basicCard);
        return new KakaoResponseDto.KakaoBasicCardResponseDto().makeResponseBody(outputs);
    }
}
