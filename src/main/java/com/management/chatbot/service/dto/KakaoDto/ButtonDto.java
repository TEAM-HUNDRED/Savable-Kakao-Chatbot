package com.management.chatbot.service.dto.KakaoDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Getter
@NoArgsConstructor
public class ButtonDto {
    private String action;
    private String label;
    private String webLinkUrl;
    private String messageText;
    private String blockId;
    private HashMap<String, String> extra;

    @Builder
    public ButtonDto(String action, String label, String webLinkUrl, String messageText, String blockId, HashMap<String, String> extra) {
        this.action = action;
        this.label = label;
        this.webLinkUrl = webLinkUrl;
        this.messageText = messageText;
        this.blockId = blockId;
        this.extra = extra;
    }
}
