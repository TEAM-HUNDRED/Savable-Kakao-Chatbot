package com.management.web.repository;

import com.management.web.domain.GiftcardProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftcardProductRepository extends JpaRepository<GiftcardProduct, Long> {

    List<GiftcardProduct> findBySaleYN(Boolean saleYN);

    @Query("SELECT p from GiftcardProduct p where p.saleYN = true order by p.price ASC ")
    List<GiftcardProduct> findBySaleYNOrderByPriceAsc(Boolean saleYN);
}
