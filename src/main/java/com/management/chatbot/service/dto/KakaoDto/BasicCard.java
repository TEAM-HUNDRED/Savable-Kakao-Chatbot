package com.management.chatbot.service.dto.KakaoDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BasicCard {
    private String title;
    private String description;
    private Thumbnail thumbnail;
    private List<ButtonDto> buttons;
    private String buttonLayout;

    @Builder
    public BasicCard(String title, String description, Thumbnail thumbnail, List<ButtonDto> buttons, String buttonLayout) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.buttons = buttons;
        this.buttonLayout = buttonLayout;
    }

    @Getter
    @NoArgsConstructor
    public static class Thumbnail {
        private String imageUrl;
        private Boolean fixedRatio;

        @Builder
        public Thumbnail(String imageUrl, Boolean fixedRatio) {
            this.imageUrl = imageUrl;
            this.fixedRatio = fixedRatio;
        }
    }
}