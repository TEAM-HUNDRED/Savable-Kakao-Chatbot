package com.management.web.repository;

import com.management.web.domain.RankingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingHistoryRepository extends JpaRepository<RankingHistory, Long> {
    RankingHistory save(RankingHistory rankingHistory);
}
