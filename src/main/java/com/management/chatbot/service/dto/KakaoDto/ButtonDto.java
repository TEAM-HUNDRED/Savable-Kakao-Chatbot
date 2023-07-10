package com.management.chatbot.service.dto.KakaoDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ButtonDto {
    private String action;
    private String label;
    private String messageText;

    @Builder
    public ButtonDto(String action, String label, String messageText) {
        this.action = action;
        this.label = label;
        this.messageText = messageText;
    }
}
