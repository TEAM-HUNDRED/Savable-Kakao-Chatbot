package com.management.chatbot.service.dto.KakaoDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class KakaoRequestDto {
    private Intent intent;
    private UserRequest userRequest;
    private Bot bot;
    private Action action;

    @Getter
    public static class Intent {
        private String id;
        private String name;
    }

    @Getter
    public static class UserRequest {
        private String timezone;
        private Map<String, String> params;
        private Block block;
        private String utterance;
        private String lang;
        private User user;
    }

    @Getter
    public static class Block {
        private String id;
        private String name;
    }

    @Getter
    public static class User {
        private String id;
        private String type;
        private Map<String, Object> properties;
    }

    @Getter
    public static class Bot {
        private String id;
        private String name;
    }

    @Getter
    public static class Action {
        private String id;
        private String name;
        private Map<String, String> clientExtra;
        private Map<String, String> params;
        private Map<String, DetailParam> detailParams;
    }

    @Getter
    public static class DetailParam {
        private String groupName;
        private String origin;
        private String value;
    }
}
