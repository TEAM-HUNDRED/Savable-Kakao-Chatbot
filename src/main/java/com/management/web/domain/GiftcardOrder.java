package com.management.web.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
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