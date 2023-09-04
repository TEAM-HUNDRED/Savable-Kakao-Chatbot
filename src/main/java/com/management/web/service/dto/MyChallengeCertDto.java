package com.management.web.service.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
public class MyChallengeCertDto {
    private Date date;
    private Integer count;

    public MyChallengeCertDto(Date date, Integer count) {
        this.date = date;
        this.count = count;
    }
}
