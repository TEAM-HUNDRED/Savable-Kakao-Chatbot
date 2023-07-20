package com.management.chatbot.web.service;

import com.management.chatbot.web.dto.MyPrivateRankingInfoDto;
import com.management.chatbot.web.dto.MyRankingInfoDto;
import com.management.chatbot.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final MemberRepository memberRepository;

    public List<MyRankingInfoDto> getMyRankingInfo(){
        return memberRepository.findRankingInfoList();
    }
    public MyPrivateRankingInfoDto getMyPrivateRankingInfo(String kakaoId) {
        return memberRepository.findByKakaoId(kakaoId);
    }


}