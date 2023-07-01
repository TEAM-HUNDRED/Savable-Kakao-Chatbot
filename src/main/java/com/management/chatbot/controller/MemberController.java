package com.management.chatbot.controller;

import com.management.chatbot.domain.Member;
import com.management.chatbot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/save") // 회원 저장
    public Long save(@RequestBody Member member) {
        System.out.println(member.getName());
        System.out.printf(member.getCertificationList().toString());
        System.out.println(member.getParticipationList().toString());
        return memberService.save(member);
    }

    @GetMapping("/test") // 챌린지 테스트
    public String test() {
        return "test";
    }
}
