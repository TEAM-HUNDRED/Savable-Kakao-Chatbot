package com.management.chatbot.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Timestamp date;
    private Boolean check;

    @Builder
    public CertInfo(String image, Timestamp date, Boolean check) {
        this.image = image;
        this.date = date;
        this.check = check;
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
