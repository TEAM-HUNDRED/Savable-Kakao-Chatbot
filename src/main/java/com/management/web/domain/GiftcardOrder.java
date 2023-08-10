package com.management.web.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class GiftcardOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kakaoId;
    private Long giftcardId;
    private Long quantity;
    private String positivePoints;
    private String negativePoints;
    private Timestamp date;
//
//    @ManyToOne
//    private GiftcardProduct giftcardProduct;

    @Builder
    public GiftcardOrder(String kakaoId, Long giftcardId, Long quantity, String positivePoints, String negativePoints, Timestamp date) {
        this.kakaoId = kakaoId;
        this.giftcardId = giftcardId;
        this.quantity = quantity;
        this.positivePoints = positivePoints;
        this.negativePoints = negativePoints;
        this.date = date;
    }
}
