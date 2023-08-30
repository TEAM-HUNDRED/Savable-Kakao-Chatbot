package com.management.chatbot.service.dto.KakaoDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class KakaoBasicCardResponseDto {
    public HashMap<String, Object> makeResponseBody(List<HashMap<String, Object>> values) {

        HashMap<String, Object> resultJson = new HashMap<>();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> template = new HashMap<>();

        for (HashMap<String, Object> value : values) {
            outputs.add(value);
        }

        template.put("outputs", outputs);

        resultJson.put("version", "2.0");
        resultJson.put("template", template);

        return resultJson;
    }
}
