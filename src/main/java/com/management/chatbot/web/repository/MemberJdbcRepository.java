package com.management.chatbot.web.repository;

import com.management.chatbot.web.dto.MyChallengeInfoDto;
import com.management.chatbot.web.dto.MyMainInfoDto;
import com.management.chatbot.web.dto.MyPrivateRankingInfoDto;
import com.management.chatbot.web.dto.MyRankingInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Repository
public class MemberJdbcRepository implements MemberRepository {

    private final JdbcTemplate template;

    public MemberJdbcRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public MyPrivateRankingInfoDto findByKakaoId(String kakaoId) {
        String sql = "WITH ranked_members AS (\n" +
                "  SELECT\n" +
                "    m.id,\n" +
                "    m.username,\n" +
                "    count(*) AS certcnt,\n" +
                "    RANK() OVER (ORDER BY count(*) DESC) AS ranking,\n" +
                "    saved_money,\n" +
                "    reward,\n" +
                "    kakao_id\n" +
                "  FROM \"member\" m,\n" +
                "       jsonb_array_elements(m.certification) AS data_row,\n" +
                "       jsonb_array_elements(data_row->'cert') AS cert_data\n" +
                "  WHERE CAST(cert_data ->> 'date' AS date) >= current_date - 6\n" +
                "  GROUP BY m.id\n" +
                "), oneMember as(\n" +
                "select * from ranked_members\n" +
                "where ranked_members.kakao_id = ?\n" +
                ")\n" +
                "SELECT\n" +
                "  r.certcnt - o.certcnt as gap ,\n" +
                "  o.username as username,\n" +
                "  o.ranking as certrank,\n" +
                "  o.certcnt as certnum,\n" +
                "  o.saved_money as savedmoney\n" +
                "  from ranked_members r, oneMember o\n" +
                "where r.ranking< o.ranking\n" +
                "order by \n" +
                "r.ranking desc, r.saved_money\n" +
                "limit 1;\n";
        MyPrivateRankingInfoDto privateRankingInfoDto = template.queryForObject(sql, rankingPrivateRowMapper(), kakaoId);
        return privateRankingInfoDto;
    }

    @Override
    public List<MyRankingInfoDto> findRankingInfoList(){
        String sql = "select\n" +
                "  m.id,\n" +
                "  m.username,\n" +
                "  count(*) as certcnt,\n" +
                "  RANK() OVER (ORDER BY count(*) DESC) AS ranking,\n" +
                " saved_money,\n" +
                " reward \n" +
                "FROM \"member\" m ,\n" +
                "     jsonb_array_elements(m.certification) AS data_row,\n" +
                "     jsonb_array_elements(data_row->'cert') AS cert_data\n" +
                "where CAST(cert_data ->> 'date' as date) >= current_date - 6\n" +
                "group by m.id\n" +
                "order by ranking, saved_money desc;";

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
                "where cert_data->>'check' = 'PASS' and m.kakao_id =?\n" +
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
                        .certRank(rs.getInt("ranking"))
                        .build()
        );
    }

    private RowMapper<MyPrivateRankingInfoDto> rankingPrivateRowMapper() {
        return ((rs, rowNum) ->
                MyPrivateRankingInfoDto.builder()
                        .username(rs.getString("username"))
                        .certRank(rs.getInt("certrank"))
                        .certNum(rs.getInt("certnum"))
                        .savedMoney(rs.getInt("savedmoney"))
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
