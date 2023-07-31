package com.management.chatbot.service;

import com.management.chatbot.Exception.DefaultException;
import com.management.chatbot.Exception.ExistMemberException;
import com.management.chatbot.domain.CertInfo;
import com.management.chatbot.domain.Member;
import com.management.chatbot.repository.MemberRepository;
import com.management.chatbot.service.dto.ChallengeResponseDto;
import com.management.chatbot.service.dto.MemberResponseDto;
import com.management.chatbot.service.dto.MemberSaveRequestDto;
import com.management.chatbot.service.dto.ParticipationSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberSaveRequestDto memberSaveRequestDto) {
        Member existMember = memberRepository.findByKakaoId(memberSaveRequestDto.getKakaoId());
        if (existMember != null) {
            throw new ExistMemberException( "세이버 " + existMember.getUsername()+"님은 이미 가입되어 있습니다.\r\r챌린지에 참여하고 싶으신 경우 채팅방 하단 \"챌린지 목록\"을 클릭하고 \"챌린지 종류\"를 선택해주세요🤖⚡️");
        }
        return memberRepository.save(memberSaveRequestDto.toEntity()).getId();
    }

    public MemberResponseDto findByKakaoId(String kakaoId) {
        Member member = memberRepository.findByKakaoId(kakaoId);
        if (member == null){
            throw new DefaultException("세이버님은 현재 Savable에 가입되어 있지 않습니다.\r채팅창에 \"닉네임 설정\"을 입력한 후 가입을 완료 해주세요🤖⚡");
        }

        return new MemberResponseDto(member);
    }

    @Transactional
    public Long participate(String kakaoId, ParticipationSaveRequestDto participationSaveRequestDto) {
        Member member = memberRepository.findByKakaoId(kakaoId); //동일한 카카오 아이디를 가진 멤버 find
        if (member == null){
            throw new DefaultException("세이버님은 현재 Savable에 가입되지 않았습니다.\r채팅창에 \"닉네임 설정\"을 입력한 후 가입을 완료한 후에 챌린지 신청을 해주세요🤖⚡");
        }
        member.addParticipation(participationSaveRequestDto.toEntity()); // 멤버에 참여 정보 추가

        return member.getId();
    }

    @Transactional
    public ParticipationSaveRequestDto certify(String kakaoId, String certificationImage, String message, ChallengeResponseDto challengeResponseDto){
        Member member = memberRepository.findByKakaoId(kakaoId); //동일한 카카오 아이디를 가진 멤버 find

        CertInfo certInfo = new CertInfo().builder()
                .image(certificationImage)
                .date(new Timestamp(System.currentTimeMillis()))
                .check(null)
                .message(message)
                .build();

        Long challengeId = challengeResponseDto.getId();
        Long savedMoney = challengeResponseDto.getSavedMoney();
        Long reward = challengeResponseDto.getReward();
        Long maxCnt = challengeResponseDto.getMaxCnt();

        // 챌린지 최대 인증 횟수 초과 여부 확인
        if (member.isMaxCertification(challengeId, maxCnt)) {
            throw new DefaultException("하루에 최대 " + maxCnt + "번 인증할 수 있습니다😢\r"
                    + "내일 다시 인증해주세요.");
        }

        ParticipationSaveRequestDto participation = member.addCertification(challengeId, certInfo, savedMoney, reward);
        return participation;
    }

    public List<ParticipationSaveRequestDto> findParticipatingChallenges(String kakaoId) {
        Member member = memberRepository.findByKakaoId(kakaoId);
        return member.getParticipatingChallenges();
    }
}