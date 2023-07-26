package com.management.chatbot.web.service;

import com.management.chatbot.web.dto.MyChallengeInfoDto;
import com.management.chatbot.web.repository.MemberWebRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipateChallengeService {
    private final MemberWebRepository memberWebRepository;

    public List<MyChallengeInfoDto> getMyParticipateChallenge(String kakaoId){
        return memberWebRepository.findParticipateChallengeList(kakaoId);
    }
}
