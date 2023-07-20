package com.management.web.service.dto;

import com.management.chatbot.service.dto.MemberResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderSaveDto {
    private String kakaoId;
    private String phoneNumber;
    private Long giftcardId;
    private Long quantity;
    private String positivePoints;
    private String negativePoints;
}