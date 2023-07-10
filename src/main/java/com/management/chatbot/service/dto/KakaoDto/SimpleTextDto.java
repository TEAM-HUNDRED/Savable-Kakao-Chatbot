package com.management.chatbot.service.dto.KakaoDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleTextDto {
    private String text;

    @Builder
    public SimpleTextDto(String text) {
        this.text = text;
    }
}
