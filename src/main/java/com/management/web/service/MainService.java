package com.management.web.service;

import com.management.web.service.dto.MyMainInfoDto;
import com.management.web.repository.MemberWebRepository;
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
