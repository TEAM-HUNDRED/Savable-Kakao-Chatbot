package com.management.web.repository;


import com.management.web.domain.GiftcardOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftcardOrderRepository extends JpaRepository<GiftcardOrder, Long> {
}
