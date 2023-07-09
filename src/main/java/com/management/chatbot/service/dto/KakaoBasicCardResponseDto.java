package com.management.chatbot.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class KakaoBasicCardResponseDto {
    public HashMap<String, Object> makeResponseBody(BasicCard entity) {

        HashMap<String, Object> resultJson = new HashMap<>();

        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> template = new HashMap<>();
        HashMap<String, Object> basicCard = new HashMap<>();

        basicCard.put("basicCard", entity);
        outputs.add(basicCard);

        template.put("outputs", outputs);

        resultJson.put("version", "2.0");
        resultJson.put("template", template);

        return resultJson;
    }
}
