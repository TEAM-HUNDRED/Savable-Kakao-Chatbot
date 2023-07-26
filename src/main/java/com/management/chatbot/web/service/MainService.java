package com.management.chatbot.web.service;

import com.management.chatbot.web.dto.MyMainInfoDto;
import com.management.chatbot.web.repository.MemberWebRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {
    private final MemberWebRepository memberWebRepository;

    public MyMainInfoDto getMainInfo(String kakaoId){
        return memberWebRepository.findMainInfoByKakaoId(kakaoId);
    }

}
