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

    @Builder
    public BasicCard(String title, String description, Thumbnail thumbnail, List<ButtonDto> buttons) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.buttons = buttons;
    }

    @Getter
    @NoArgsConstructor
    public static class Thumbnail {
        private String imageUrl;

        @Builder
        public Thumbnail(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}