package com.management.chatbot.service.dto.KakaoDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Getter
@NoArgsConstructor
public class CarouselDto {
    private final String type = "basicCard";
    private List<BasicCard> items;

    @Builder
    public CarouselDto(List<BasicCard> items) {
        this.items = items;
    }
}
