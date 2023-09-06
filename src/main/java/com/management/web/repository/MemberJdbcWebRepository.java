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

    @Value("${certList}")
    String certListQuery;


    @Override
    public MyPrivateRankingInfoDto findPrivateRnkingByKakaoId(String kakaoId) {
        String sql = "with rankingCert as(\n" +
                "select\n" +
                "  m.kakao_id,\n" +
                "  m.username,\n" +
                "  cert_data->>'date' AS date,\n" +
                "  cert_data->>'check' as check,\n" +
                "  data_row ->> 'challenge_id' as c_id\n" +
                "FROM \"member\" m ,\n" +
                "     jsonb_array_elements(m.certification) AS data_row,\n" +
                "     jsonb_array_elements(data_row->'cert') AS cert_data\n" +
                "where CAST(cert_data ->> 'date' as date) >= date_trunc('week',current_date)::date\n" +
                "and cert_data->>'check' != 'FAIL'),\n" +
                "ranked_members as (select r.username as username,\n" +
                "sum(c.saved_money) as smoney,\n" +
                "count(*) as cnt,\n" +
                "r.kakao_id as kakao_id,\n" +
                "rank() over (order by sum(c.saved_money) desc,count(*) desc)\n" +
                "from rankingCert r join challenge c on r.c_id::int = c.id\n" +
                "group by r.username,r.kakao_id),\n" +
                "oneMember as(\n" +
                "select * from ranked_members rr\n" +
                "where rr.kakao_id = ?\n" +
                ")\n" +
                "select\n" +
                "  case when o.rank::integer = 1::integer then 0 else rrr.smoney-o.smoney end as gap,\n" +
                "  o.username as username,\n" +
                "  o.rank as myrank,\n" +
                "  o.cnt as mycnt,\n" +
                "  o.smoney as mysmoney\n" +
                "  from ranked_members rrr, oneMember o\n" +
                "where rrr.rank< o.rank or o.rank = 1\n" +
                "order by rrr.smoney,rrr.rank desc\n" +
                "limit 1;\n";
        String sql_null = "with rankingCert as(\n" +
                "select\n" +
                "  m.kakao_id,\n" +
                "  m.username,\n" +
                "  cert_data->>'date' AS date,\n" +
                "  cert_data->>'check' as check,\n" +
                "  data_row ->> 'challenge_id' as c_id\n" +
                "FROM \"member\" m ,\n" +
                "     jsonb_array_elements(m.certification) AS data_row,\n" +
                "     jsonb_array_elements(data_row->'cert') AS cert_data\n" +
                "where CAST(cert_data ->> 'date' as date) >= date_trunc('week',current_date)::date\n" +
                "and cert_data->>'check' != 'FAIL'),\n" +
                "rankingResult as (\n" +
                "select \n" +
                "\tr.username as username,\n" +
                "\tsum(c.saved_money) as smoney,\n" +
                "\tcount(*) as cnt,\n" +
                "\tr.kakao_id,\n" +
                "\trank() over (order by sum(c.saved_money) desc,  count(*) desc)\n" +
                "from rankingCert r join challenge c on r.c_id::int = c.id\n" +
                "group by r.username,r.kakao_id)\n" +
                "select\n" +
                "\tmin(rr.smoney) as gap,\n" +
                "\tmax(rr.rank)+1 as myRank,\n" +
                "\tm.username as username,\n" +
                "\t0 as mysmoney,\n" +
                "\t0 as mycnt\n" +
                "from rankingResult rr, member m\n" +
                "where m.kakao_id=?\n" +
                "group by m.username;";

        MyPrivateRankingInfoDto privateRankingInfoDto;
        try {
            privateRankingInfoDto = template.queryForObject(sql, rankingPrivateRowMapper(), kakaoId);
        }catch(IncorrectResultSizeDataAccessException error) {
            privateRankingInfoDto = template.queryForObject(sql_null, rankingPrivateRowMapper(), kakaoId);
        }
        return privateRankingInfoDto;

    }

    @Override
    public List<MyRankingInfoDto> findRankingInfoList(){
        String sql = "with rankingCert as(\n" +
                "select\n" +
                "  m.kakao_id,\n" +
                "  m.username,\n" +
                "  cert_data->>'date' AS date,\n" +
                "  cert_data->>'check' as check,\n" +
                "  data_row ->> 'challenge_id' as c_id\n" +
                "FROM \"member\" m ,\n" +
                "     jsonb_array_elements(m.certification) AS data_row,\n" +
                "     jsonb_array_elements(data_row->'cert') AS cert_data\n" +
                "where CAST(cert_data ->> 'date' as date) >= date_trunc('week',current_date)::date\n" +
                "and cert_data->>'check' != 'FAIL')\n" +
                "select r.username as username, r.kakao_id as kakaoId, sum(c.saved_money) as smoney, count(*) as cnt,\n" +
                "rank() over (order by sum(c.saved_money) desc,  count(*) desc)\n" +
                "from rankingCert r join challenge c on r.c_id::int = c.id\n" +
                "group by r.username, r.kakao_id;";
        List<MyRankingInfoDto> certRankingList = template.query(sql, rankingRowMapper());
        return certRankingList;
    }

    @Override
    public List<MyChallengeInfoDto> findParticipateChallengeList(String kakaoId){
        String sql="SELECT\n" +
                "  c.title AS title,\n" +
                "  m.username AS username,\n" +
                "  data_row ->> 'certificationCnt' AS cnt,\n" +
                "  c.reward*(data_row ->> 'certificationCnt')::int AS reward,\n" +
                "  c.saved_money*(data_row ->> 'certificationCnt')::int AS saved_money,\n" +
                "  data_row ->> 'challengeId' AS challengeId\n" +
                "FROM \"member\" m ,\n" +
                "     jsonb_array_elements(m.participation) AS data_row JOIN challenge c ON (data_row ->> 'challengeId')::integer=c.id\n" +
                "WHERE m. kakao_id =?\n" +
                "AND current_date >= (data_row ->> 'startDate')::date\n" +
                "\tAND current_date <= (data_row ->> 'endDate')::date;";
        List<MyChallengeInfoDto> myChallengeList = template.query(sql, challengeRowMapper(),kakaoId);
        return myChallengeList;
    }

    @Override
    public List<MyChallengeCertDto> findChallengeCertList(Integer challengeId, String kakaoId){

        List<MyChallengeCertDto> myChallengeCertList = template.query(certListQuery, challengeCertRowMapper(),kakaoId,challengeId);
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
