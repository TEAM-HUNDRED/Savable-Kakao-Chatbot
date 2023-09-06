package com.management.web.repository;

import com.management.web.service.dto.*;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Slf4j
@Repository
@PropertySource("classpath:sql.xml")
public class MemberJdbcWebRepository implements MemberWebRepository {
    private final JdbcTemplate template;

    public MemberJdbcWebRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Value("${privateRankingSQL}")
    String privateRanking;
    @Value("${privateRankingNullSQL}")
    String privateRankingNull;
    @Override
    public MyPrivateRankingInfoDto findPrivateRankingByKakaoId(String kakaoId) {
        MyPrivateRankingInfoDto privateRankingInfoDto;
        try {
            privateRankingInfoDto = template.queryForObject(privateRanking, rankingPrivateRowMapper(), kakaoId);
        }catch(IncorrectResultSizeDataAccessException error) {
            privateRankingInfoDto = template.queryForObject(privateRankingNull, rankingPrivateRowMapper(), kakaoId);
        }
        return privateRankingInfoDto;
    }

    @Value("${rankingListSQL}")
    String rankingList;
    @Override
    public List<MyRankingInfoDto> findRankingInfoList(){
        List<MyRankingInfoDto> certRankingList = template.query(rankingList, rankingRowMapper());
        return certRankingList;
    }

    @Value("${participateChallengeListSQL}")
    String participateChallengeList;
    @Override
    public List<MyChallengeInfoDto> findParticipateChallengeList(String kakaoId){
        List<MyChallengeInfoDto> myChallengeList = template.query(participateChallengeList, challengeRowMapper(),kakaoId);
        return myChallengeList;
    }

    @Value("${challengeCertListSQL}")
    String challengeCertListQuery;
    @Override
    public List<MyChallengeCertDto> findChallengeCertList(Integer challengeId, String kakaoId){

        List<MyChallengeCertDto> myChallengeCertList = template.query(challengeCertListQuery, challengeCertRowMapper(),kakaoId,challengeId);
        return myChallengeCertList;
    }

    public MyMainInfoDto findMainInfoByKakaoId(String kakaoId){
        String sql = "select username, saved_money, reward\n" +
                "from \"member\" m \n" +
                "where kakao_id =?;";
        MyMainInfoDto mainInfoDto = template.queryForObject(sql, mainInfoDtoRowMapper(),kakaoId);
        return mainInfoDto;
    }

    private RowMapper<MyRankingInfoDto> rankingRowMapper() {
        return ((rs, rowNum) ->
                MyRankingInfoDto.builder()
                        .username(rs.getString("username"))
                        .certRank(rs.getInt("rank"))
                        .kakaoId(rs.getString("kakaoId"))
                        .build()
        );
    }

    private RowMapper<MyPrivateRankingInfoDto> rankingPrivateRowMapper() {
        return ((rs, rowNum) ->
                MyPrivateRankingInfoDto.builder()
                        .username(rs.getString("username"))
                        .certRank(rs.getInt("myrank"))
                        .certNum(rs.getInt("mycnt"))
                        .savedMoney(rs.getInt("mysmoney"))
                        .gap(rs.getInt("gap"))
                        .build()
        );
    }

    private RowMapper<MyChallengeInfoDto> challengeRowMapper() {
        return ((rs, rowNum) ->
                MyChallengeInfoDto.builder()
                        .title(rs.getString("title"))
                        .savedMoney(rs.getInt("saved_money"))
                        .reward(rs.getInt("reward"))
                        .username(rs.getString("username"))
                        .cnt(rs.getInt("cnt"))
                        .challengeId(rs.getInt("challengeId"))
                        .build()
        );
    }

    private RowMapper<MyChallengeCertDto> challengeCertRowMapper() {
        return ((rs, rowNum) ->
                MyChallengeCertDto.builder()
                        .date(rs.getDate("day").toLocalDate())
                        .count(rs.getInt("count"))
                        .build()
        );
    }

    private RowMapper<MyMainInfoDto> mainInfoDtoRowMapper(){
        return(((rs, rowNum) ->
                MyMainInfoDto.builder()
                        .username(rs.getString("username"))
                        .savedMoney(rs.getInt("saved_money"))
                        .reward(rs.getInt("reward"))
                        .build()));
    }
}
