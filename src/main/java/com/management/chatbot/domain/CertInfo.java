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
    private String message;

    @Builder
    public CertInfo(String image, Timestamp date, Boolean check, String message) {
        this.image = image;
        this.date = date;
        this.check = CheckStatus.UNCHECKED;
        this.message = message;
    }

    @Override
    public String toString(){
        return "CertInfo{" +
                "image=" + image +
                ", date=" + date +
                ", check=" + check +
                ", message=" + message +
                "}";
    }
}
