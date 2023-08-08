package com.management.web.repository;

import com.management.web.service.dto.MyChallengeInfoDto;
import com.management.web.service.dto.MyMainInfoDto;
import com.management.web.service.dto.MyPrivateRankingInfoDto;
import com.management.web.service.dto.MyRankingInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Repository
public class MemberJdbcWebRepository implements MemberWebRepository {

    private final JdbcTemplate template;

    public MemberJdbcWebRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

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
        String sql="with chall as (\n" +
                "SELECT\n" +
                "  data_row ->>'challenge_id' as c_id,\n" +
                "  m.kakao_id,\n" +
                "  count(*) cnt,\n" +
                "  m.username \n" +
                "FROM \"member\" m ,\n" +
                "     jsonb_array_elements(m.certification) AS data_row,\n" +
                "     jsonb_array_elements(data_row->'cert') AS cert_data\n" +
                "where cert_data->>'check' != 'FAIL' and m.kakao_id =?\n" +
                "group by m.kakao_id,c_id,m.username)\n" +
                "select chall.kakao_id, c2.title,chall.username,\n" +
                "c2.saved_money*chall.cnt as saved_money,\n" +
                "c2.reward*chall.cnt as reward,chall.cnt as cnt\n" +
                "from chall join challenge c2 on chall.c_id::int = c2.id;";
        List<MyChallengeInfoDto> myChallengeList = template.query(sql, challengeRowMapper(),kakaoId);
        return myChallengeList;
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
