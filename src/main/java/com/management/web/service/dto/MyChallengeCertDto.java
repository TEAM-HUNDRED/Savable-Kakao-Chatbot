package com.management.web.service.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class MyChallengeCertDto {
    private Timestamp date;
    private Integer count;

    public MyChallengeCertDto(Timestamp date, Integer count) {
        this.date = date;
        this.count = count;
    }
}
