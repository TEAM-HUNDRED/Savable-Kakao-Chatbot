package com.management.web.repository;


import com.management.web.domain.GiftcardOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftcardOrderRepository extends JpaRepository<GiftcardOrder, Long> {
}
