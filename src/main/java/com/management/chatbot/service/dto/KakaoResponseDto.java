package com.management.chatbot.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoResponseDto {

    public HashMap<String ,Object> makeResponseBody(String message){
        HashMap<String, Object> resultJson = new HashMap<>();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> template = new HashMap<>();
        HashMap<String, Object> simpleText = new HashMap<>();
        HashMap<String, Object> text = new HashMap<>();

        text.put("text", message);

        simpleText.put("simpleText", text);
        outputs.add(simpleText);

        template.put("outputs", outputs);

        resultJson.put("version", "2.0");
        resultJson.put("template", template);

        return resultJson;
    }

}
