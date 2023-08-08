package com.management.web.repository;

import com.management.web.service.dto.MyChallengeInfoDto;
import com.management.web.service.dto.MyMainInfoDto;
import com.management.web.service.dto.MyPrivateRankingInfoDto;
import com.management.web.service.dto.MyRankingInfoDto;

import java.util.List;

public interface MemberWebRepository {


    MyPrivateRankingInfoDto findPrivateRnkingByKakaoId(String kakaoId);

    List<MyRankingInfoDto> findRankingInfoList();

    List<MyChallengeInfoDto> findParticipateChallengeList(String kakaoId);

    MyMainInfoDto findMainInfoByKakaoId(String kakaoId);
}
