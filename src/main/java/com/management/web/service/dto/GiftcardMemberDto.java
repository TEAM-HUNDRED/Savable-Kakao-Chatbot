package com.management.web.service.dto;

import com.management.chatbot.service.dto.MemberResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GiftcardMemberDto {
    private String username;
    private Long reward;
    private Long savedMoney;
    private String phoneNumber;

    public GiftcardMemberDto(MemberResponseDto entity){
        this.username = entity.getUsername();
        this.reward = entity.getReward();
        this.savedMoney = entity.getSavedMoney();
        this.phoneNumber = entity.getPhoneNumber();
    }
}
