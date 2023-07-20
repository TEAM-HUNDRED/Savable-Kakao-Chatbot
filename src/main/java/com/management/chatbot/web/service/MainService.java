package com.management.chatbot.web.service;

import com.management.chatbot.web.dto.MyMainInfoDto;
import com.management.chatbot.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {
    private final MemberRepository memberRepository;

    public MyMainInfoDto getMainInfo(String kakaoId){
        return memberRepository.findMainInfoByKakaoId(kakaoId);
    }

}
