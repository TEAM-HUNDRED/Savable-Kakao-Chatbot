package com.management.chatbot.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class Certification implements Serializable {

    private Long challenge_id; // 챌린지 id

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private List<CertInfo> cert = new ArrayList<>();

    @Builder
    public Certification(Long id, List<CertInfo> cert) {
        this.challenge_id = id;
        this.cert = cert;
    }

    @Override
    public String toString(){
        return "Certification{" +
                "challenge_id=" + challenge_id +
                ", cert=" + cert +
                "}";
    }

    public void addCert(CertInfo certInfo){
        this.cert.add(certInfo);
    }
}
