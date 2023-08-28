package com.management.web.repository;

import com.management.web.service.dto.*;

import java.util.List;

public interface MemberWebRepository {


    MyPrivateRankingInfoDto findPrivateRnkingByKakaoId(String kakaoId);

    List<MyRankingInfoDto> findRankingInfoList();

    List<MyChallengeInfoDto> findParticipateChallengeList(String kakaoId);
    List<MyChallengeCertDto> findChallengeCertList(Integer challengeId, String kakaoId);

    MyMainInfoDto findMainInfoByKakaoId(String kakaoId);
}
