package com.management.web.service.dto;

import com.management.web.domain.GiftcardOrder;
import com.management.web.domain.GiftcardOrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
    private String status;

    public GiftcardOrder toEntity(){
        return GiftcardOrder.builder()
                .kakaoId(kakaoId)
                .giftcardId(giftcardId)
                .quantity(quantity)
                .positivePoints(positivePoints)
                .negativePoints(negativePoints)
                .date(date)
                .status(status)
                .build();
    }
}
