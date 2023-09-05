package com.management.web.service.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Builder
public class MyChallengeCertDto {
    private LocalDate date;
    private Integer count;

    public MyChallengeCertDto(LocalDate date, Integer count) {
        this.date = date;
        this.count = count;
    }
}
