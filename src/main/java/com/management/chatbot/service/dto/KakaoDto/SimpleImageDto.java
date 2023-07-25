package com.management.chatbot.service.dto.KakaoDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleImageDto {
    private String imageUrl;
    private String altText;
    private boolean fixedRatio;

    @Builder
    public SimpleImageDto(String imageUrl, String altText, boolean fixedRatio) {
        this.imageUrl = imageUrl;
        this.altText = altText;
        this.fixedRatio = fixedRatio;
    }
}
