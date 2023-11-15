package com.management.web.service;

import com.management.chatbot.domain.Member;
import com.management.chatbot.repository.MemberRepository;
import com.management.web.domain.RankingHistory;
import com.management.web.repository.MemberWebRepository;
import com.management.web.repository.RankingHistoryRepository;
import com.management.web.service.dto.MyPrivateRankingInfoDto;
import com.management.web.service.dto.MyRankingInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final MemberWebRepository memberWebRepository;
    private final MemberRepository memberRepository;
    private final RankingHistoryRepository rankingHistoryRepository;

    public List<MyRankingInfoDto> getMyRankingInfo(){
        return memberWebRepository.findRankingInfoList();
    }
    public MyPrivateRankingInfoDto getMyPrivateRankingInfo(String kakaoId) {
        return memberWebRepository.findPrivateRankingByKakaoId(kakaoId);
    }

    @Transactional
//    @Scheduled(cron = "0 59 23 * * 0") // 매주 일요일 23시 59분에 실행 // 2023.11.15 서비스 종료로 미사용
    public void updateRanking() {
        List<MyRankingInfoDto> rankingInfoList = memberWebRepository.findRankingInfoList();
        for (MyRankingInfoDto rankingInfo : rankingInfoList) {
            if (rankingInfo.getCertRank() > 5){
                break;
            }
            Long additionalReward = Long.valueOf(getAdditionalRankingReward(rankingInfo.getCertRank()));
            String kakaoId = rankingInfo.getKakaoId();
            Member member = memberRepository.findByKakaoId(kakaoId);

            // 현재 시간 timestamp
            rankingHistoryRepository.save(RankingHistory.builder()
                    .kakaoId(kakaoId)
                    .reward(additionalReward)
                    .date(new Timestamp(System.currentTimeMillis()))
                    .build());
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
