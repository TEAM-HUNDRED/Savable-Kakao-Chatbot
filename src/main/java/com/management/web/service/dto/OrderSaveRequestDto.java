package com.management.web.service.dto;

import com.management.web.domain.GiftcardOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderSaveRequestDto {
    private String kakaoId;
    private String phoneNumber;
    private Long giftcardId;
    private Long quantity;
    private String positivePoints;
    private String negativePoints;
    private final Timestamp date = new Timestamp(System.currentTimeMillis());

    public GiftcardOrder toEntity(){
        return GiftcardOrder.builder()
                .kakaoId(kakaoId)
                .giftcardId(giftcardId)
                .quantity(quantity)
                .positivePoints(positivePoints)
                .negativePoints(negativePoints)
                .date(date)
                .build();
    }
}