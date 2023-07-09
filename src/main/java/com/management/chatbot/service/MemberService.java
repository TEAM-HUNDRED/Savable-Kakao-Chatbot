package com.management.chatbot.service;

import com.management.chatbot.Exception.MaxCertificationException;
import com.management.chatbot.domain.CertInfo;
import com.management.chatbot.domain.Member;
import com.management.chatbot.repository.MemberRepository;
import com.management.chatbot.service.dto.ChallengeResponseDto;
import com.management.chatbot.service.dto.MemberResponseDto;
import com.management.chatbot.service.dto.MemberSaveRequestDto;
import com.management.chatbot.service.dto.ParticipationSaveRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberSaveRequestDto requestDto) {
        return memberRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public MemberResponseDto findByKakaoId(String kakaoId) {
        Member member = memberRepository.findByKakaoId(kakaoId);
        return new MemberResponseDto(member);
    }

    @Transactional
    public Long participate(String kakaoId, ParticipationSaveRequestDto participationSaveRequestDto) {
        Member member = memberRepository.findByKakaoId(kakaoId); //동일한 카카오 아이디를 가진 멤버 find
        member.addParticipation(participationSaveRequestDto.toEntity()); // 멤버에 참여 정보 추가

        return member.getId();
    }

    @Transactional
    public Member certify(String kakaoId, Long challengeId, String certificationImage, ChallengeResponseDto challengeResponseDto){
        Member member = memberRepository.findByKakaoId(kakaoId); //동일한 카카오 아이디를 가진 멤버 find
        CertInfo certInfo = new CertInfo().builder()
                .image(certificationImage)
                .date(new Timestamp(System.currentTimeMillis()))
                .check(null)
                .build();

        Long challengeId = challengeResponseDto.getId();
        Long savedMoney = challengeResponseDto.getSavedMoney();
        Long reward = challengeResponseDto.getReward();
        Long maxCnt = challengeResponseDto.getMaxCnt();

        // 챌린지 최대 인증 횟수 초과 여부 확인
        if (member.isMaxCertification(challengeId, maxCnt)) {
            String message = "하루에 최대 " + maxCnt + "번 인증할 수 있습니다😢\r"
                    + "내일 다시 인증해주세요.";
            throw new MaxCertificationException(message);
        }

        member.addCertification(challengeId, certInfo, savedMoney, reward);
        return member;
    }
}