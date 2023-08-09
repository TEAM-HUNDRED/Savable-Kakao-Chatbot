package com.management.web.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class GiftcardProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String name;

    private Long price;

    private String image;

    @Column(name = "sale_YN")
    private Boolean saleYN;
//
//    @OneToMany(mappedBy = "giftcardProduct")
//    private List<GiftcardOrder> giftcardOrderList;
}
