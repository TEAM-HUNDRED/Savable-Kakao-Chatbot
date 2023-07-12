package com.management.chatbot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CertInfo implements Serializable{
    private String image;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "Asia/Seoul")
    private Timestamp date;
    @Enumerated(EnumType.STRING)
    private CheckStatus check;

    @Builder
    public CertInfo(String image, Timestamp date, Boolean check) {
        this.image = image;
        this.date = date;
        this.check = CheckStatus.UNCHECKED;
    }

    @Override
    public String toString(){
        return "CertInfo{" +
                "image=" + image +
                ", date=" + date +
                ", check=" + check +
                "}";
    }
}
