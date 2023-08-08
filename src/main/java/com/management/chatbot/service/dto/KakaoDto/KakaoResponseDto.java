package com.management.chatbot.service.dto.KakaoDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public HashMap<String ,Object> makeResponseDate(HashMap<String, String> data){
        HashMap<String, Object> resultJson = new HashMap<>();

        resultJson.put("version", "2.0");
        resultJson.put("data", data);

        return resultJson;
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class KakaoBasicCardResponseDto {
        public HashMap<String, Object> makeResponseBody(List<HashMap<String, Object>> values) {

            HashMap<String, Object> resultJson = new HashMap<>();

            List<HashMap<String, Object>> outputs = new ArrayList<>();
            HashMap<String, Object> template = new HashMap<>();
            HashMap<String, Object> basicCard = new HashMap<>();

            for (HashMap<String, Object> value : values) {
                outputs.add(value);
            }

            template.put("outputs", outputs);

            resultJson.put("version", "2.0");
            resultJson.put("template", template);

            return resultJson;
        }
    }
}
