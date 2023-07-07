package com.management.chatbot.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class KakaoImageRequestDto {
    private Bot bot;
    private Intent intent;
    private Action action;
    private UserRequest userRequest;
    private List<Object> contexts;

    @Getter
    public static class Bot {
        private String id;
        private String name;
    }

    @Getter
    public static class Intent {
        private String id;
        private String name;
        private Extra extra;
    }

    @Getter
    public static class Extra {
        private Reason reason;

        // 생성자, getter, setter 등 필요한 메소드를 추가해주세요.

        // Reason 클래스
        public static class Reason {
            private int code;
            private String message;

            // 생성자, getter, setter 등 필요한 메소드를 추가해주세요.
        }
    }

    @Getter
    public static class Action {
        private String id;
        private String name;
        private Map<String, String> params;
        private Map<String, DetailParams> detailParams;
        private Map<String, String> clientExtra;
    }

    @Getter
    public static class DetailParams {
        private String groupName;
        private String origin;
        private String value;
    }

    @Getter
    public static class UserRequest {
        private Block block;
        private User user;
        private String utterance;
        private Params params;
        private String lang;
        private String timezone;

        @Getter
        public static class Block {
            private String id;
            private String name;

            // 생성자, getter, setter 등 필요한 메소드를 추가해주세요.
        }

        @Getter
        public static class User {
            private String id;
            private String type;
            private Properties properties;

            @Getter
            public static class Properties {
                private String botUserKey;
                private boolean isFriend;
                private String plusfriendUserKey;
                private String bot_user_key;
                private String plusfriend_user_key;
            }
        }

        @Getter
        public static class Params {
            private String surface;
            private Media media;

            @Getter
            public static class Media {
                private String type;
                private String url;
            }
        }
    }
}