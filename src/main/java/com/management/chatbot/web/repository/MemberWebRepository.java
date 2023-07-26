package com.management.chatbot.web.repository;

import com.management.chatbot.web.dto.MyChallengeInfoDto;
import com.management.chatbot.web.dto.MyMainInfoDto;
import com.management.chatbot.web.dto.MyPrivateRankingInfoDto;
import com.management.chatbot.web.dto.MyRankingInfoDto;

import java.util.List;

public interface MemberWebRepository {


    MyPrivateRankingInfoDto findPrivateRnkingByKakaoId(String kakaoId);

    List<MyRankingInfoDto> findRankingInfoList();

    List<MyChallengeInfoDto> findParticipateChallengeList(String kakaoId);

    MyMainInfoDto findMainInfoByKakaoId(String kakaoId);
}
