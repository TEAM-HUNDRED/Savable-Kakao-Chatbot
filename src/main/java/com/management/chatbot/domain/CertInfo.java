package com.management.chatbot.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DialectOverride;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
public class CertInfo implements Serializable{
    private String image;
    private Timestamp date;
    private Boolean check;

    @Builder
    public CertInfo(String image, Timestamp date) {
        this.image = image;
        this.date = date;
        this.check = null;
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
