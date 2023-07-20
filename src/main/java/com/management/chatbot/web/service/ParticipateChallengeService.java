package com.management.chatbot.web.service;

import com.management.chatbot.web.dto.MyChallengeInfoDto;
import com.management.chatbot.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipateChallengeService {
    private final MemberRepository memberRepository;

    public List<MyChallengeInfoDto> getMyParticipateChallenge(String kakaoId){
        return memberRepository.findParticipateChallengeList(kakaoId);
    }
}
