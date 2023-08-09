package com.management.web.repository;


import com.management.web.domain.GiftcardOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftcardOrderRepository extends JpaRepository<GiftcardOrder, Long> {
    @Override
    List<GiftcardOrder> findAll();

    @Query("select g, p from GiftcardOrder g join GiftcardProduct p on g.giftcardId = p.id")
    List<Object[]> findJoinedAll();

    @Query("select g, p from GiftcardOrder g join GiftcardProduct p on g.giftcardId = p.id where g.kakaoId = :kakaoId")
    List<Object[]> findByKakaoId(@Param("kakaoId") String kakaoId);
}
