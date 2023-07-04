package com.management.chatbot.controller;

import com.management.chatbot.service.dto.KakaoRequestDto;
import com.management.chatbot.domain.CertInfo;
import com.management.chatbot.domain.Certification;
import com.management.chatbot.domain.Member;
import com.management.chatbot.domain.Participation;
import com.management.chatbot.service.MemberService;
import com.management.chatbot.service.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/new-member") // 회원 가입
    public HashMap<String, Object> joinMember(@RequestBody KakaoRequestDto kakaoRequestDto) {

        String kakaoId = kakaoRequestDto.getUserRequest().getUser().getId();
        String name = kakaoRequestDto.getAction().getParams().get("userNickname");

        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .name(name)
                .kakaoId(kakaoId)
                .reward(0L)
                .savedMoney(0L)
                .build();

        memberService.save(requestDto); // 새로운 유저 저장

        HashMap<String, Object> resultJson = new HashMap<>();

        try {
            List<HashMap<String, Object>> outputs = new ArrayList<>();
            List<HashMap<String, Object>> buttons = new ArrayList<>();
            HashMap<String, Object> template = new HashMap<>();
            HashMap<String, Object> simpleText = new HashMap<>();
            HashMap<String, Object> text = new HashMap<>();
            HashMap<String, Object> button = new HashMap<>();

            button.put("label", "챌린지 신청하기");
            button.put("action", "block");
            buttons.add(button);
            text.put("buttons", buttons);
            text.put("text", requestDto.getName() + "님 안녕하세요!");

            simpleText.put("simpleText", text);
            outputs.add(simpleText);

            template.put("outputs", outputs);

            resultJson.put("version", "2.0");
            resultJson.put("template", template);

        } catch (Exception e) {

        }

        System.out.println("KKORestAPI.callAPI");

        return resultJson;
    }


    @GetMapping("/test") // 챌린지 테스트
    public String test() {
        Long nowDate = System.currentTimeMillis();
        Participation participationA = new Participation();
        participationA.setEndDate(new Timestamp(nowDate));
        participationA.setStartDate(new Timestamp(nowDate));
        participationA.setCertificationCnt(1);
        participationA.setChallengeId(1L);

        Participation participationB = new Participation();
        participationB.setEndDate(new Timestamp(nowDate));
        participationB.setStartDate(new Timestamp(nowDate));
        participationB.setCertificationCnt(5);
        participationB.setChallengeId(2L);


        CertInfo certInfoA = new CertInfo();
        certInfoA.setDate(new Timestamp(nowDate));
        certInfoA.setImage("test_url22");
        CertInfo certInfoB = new CertInfo();
        certInfoB.setDate(new Timestamp(nowDate));
        certInfoB.setImage("test_url33");

        Certification certificationA = new Certification();
        certificationA.setId(1L);
        certificationA.setCert(Arrays.asList(certInfoA, certInfoB));

        Member member = new Member();
        member.setName("test");
        member.setSavedMoney(100000L);
        member.setReward(1000L);
        member.setKakaoId("test_kakaoId");
        member.setParticipationList(
                Arrays.asList(participationA, participationB)
        );
        member.setCertificationList(
                Arrays.asList(certificationA)
        );
        member.setName("test_name");

        return "test";
    }
}
