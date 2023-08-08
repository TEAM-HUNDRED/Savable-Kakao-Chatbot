package com.management.web.service;

import com.management.chatbot.domain.Member;
import com.management.chatbot.repository.MemberRepository;
import com.management.web.service.dto.MyPrivateRankingInfoDto;
import com.management.web.service.dto.MyRankingInfoDto;
import com.management.web.repository.MemberWebRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final MemberWebRepository memberWebRepository;
    private final MemberRepository memberRepository;

    public List<MyRankingInfoDto> getMyRankingInfo(){
        return memberWebRepository.findRankingInfoList();
    }
    public MyPrivateRankingInfoDto getMyPrivateRankingInfo(String kakaoId) {
        return memberWebRepository.findPrivateRnkingByKakaoId(kakaoId);
    }

    @Transactional
    @Scheduled(cron = "1 0 0 * * 1") // 매주 월요일 00:00:01 마다 실행
    public void updateRanking() {

        List<MyRankingInfoDto> rankingInfoList = memberWebRepository.findRankingInfoList();
        for (MyRankingInfoDto rankingInfo : rankingInfoList) {
            if (rankingInfo.getCertRank() > 5){
                break;
            }
            Long additionalReward = Long.valueOf(getAdditionalRankingReward(rankingInfo.getCertRank()));
            String kakaoId = rankingInfo.getKakaoId();
            Member member = memberRepository.findByKakaoId(kakaoId);
            member.updateReward(additionalReward);
        }
    }

    public int getAdditionalRankingReward(Integer rank) {
        int additionalAmount = 0;
        switch (rank) {
            case 1:
                additionalAmount = 1000;
                break;
            case 2:
                additionalAmount = 800;
                break;
            case 3:
                additionalAmount = 500;
                break;
            case 4:
            case 5:
                additionalAmount = 300;
                break;
            default:
                // 기본값은 0원
                break;
        }
        return additionalAmount;
    }

}