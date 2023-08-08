package com.management.web.service;

import com.management.web.service.dto.MyChallengeInfoDto;
import com.management.web.repository.MemberWebRepository;
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
