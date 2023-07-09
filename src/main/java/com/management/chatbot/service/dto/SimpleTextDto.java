package com.management.chatbot.service.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SimpleTextDto {
    private String text;

    @Builder
    public SimpleTextDto(String text) {
        this.text = text;
    }
}
