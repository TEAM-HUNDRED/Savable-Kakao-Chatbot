package com.management.chatbot.service;

import com.management.chatbot.domain.Challenge;
import com.management.chatbot.service.dto.ChallengeResponseDto;
import com.management.chatbot.service.dto.ChallengeSaveRequestDto;
import com.management.chatbot.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Transactional(readOnly = true) // 챌린지 id로 하나의 챌린지 찾기
    public ChallengeResponseDto findById(Long id) {
        Challenge entity = challengeRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 챌린지는 존재하지 않습니다. id= " + id));

        return new ChallengeResponseDto(entity);
    }


    @Transactional // 새로운 챌린지 추가
    public Long save(ChallengeSaveRequestDto requestDto) {
        return challengeRepository.save(requestDto.toEntity()).getId();
    }
}
