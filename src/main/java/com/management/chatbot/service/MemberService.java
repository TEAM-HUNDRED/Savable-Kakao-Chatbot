package com.management.chatbot.service;

import com.management.chatbot.domain.Member;
import com.management.chatbot.repository.MemberRepository;
import com.management.chatbot.service.dto.MemberSaveRequestDto;
import com.management.chatbot.service.dto.ParticipationSaveRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberSaveRequestDto requestDto) {
        return memberRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long participate(String kakaoId, ParticipationSaveRequestDto participationSaveRequestDto) {
        Member member = memberRepository.findByKakaoId(kakaoId); //동일한 카카오 아이디를 가진 멤버 find
        member.addParticipation(participationSaveRequestDto.toEntity()); // 멤버에 참여 정보 추가

        return member.getId();
    }

}