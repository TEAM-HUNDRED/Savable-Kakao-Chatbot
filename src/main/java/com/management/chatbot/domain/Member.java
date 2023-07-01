package com.management.chatbot.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Long savedMoney;

    @Column(nullable = false)
    private Long reward;

    // 참여자 정보
    @ElementCollection
    @Convert(converter = ListToJsonConverter.class)
    private List<Participation> participationList = new ArrayList<>();

    // 인증 정보
    @ElementCollection
    @Convert(converter = ListToJsonConverter.class)
    private List<Certification> certificationList = new ArrayList<>();

    @Column(nullable = false)
    private String kakaoId;

    @Builder
    public Member(String name, String nickname, List<Participation> participationList, List<Certification> certificationList, Long savedMoney, Long reward, String kakaoId) {
        this.name = name;
        this.nickname = nickname;
        this.participationList = participationList;
        this.certificationList = certificationList;
        this.savedMoney = savedMoney;
        this.reward = reward;
        this.kakaoId = kakaoId;
    }

    public void update(String name, String nickname, List<Participation> participationList, List<Certification> certificationList, Long savedMoney, Long reward, String kakaoId) {
        this.name = name;
        this.nickname = nickname;
        this.participationList = participationList;
        this.certificationList = certificationList;
        this.savedMoney = savedMoney;
        this.reward = reward;
        this.kakaoId = kakaoId;
    }
}