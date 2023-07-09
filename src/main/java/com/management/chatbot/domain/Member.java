package com.management.chatbot.domain;

import com.management.chatbot.Exception.AlreadyJoinedException;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    // 참여자 정보
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", name = "participation")
    private List<Participation> participationList = new ArrayList<Participation>();

    // 인증 정보
    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb", name= "certification")
    private List<Certification> certificationList = new ArrayList<Certification>();

    private Long savedMoney;
    private Long reward;
    private String kakaoId;
    private String kakaoName;

    @Builder
    public Member(String username, List<Participation> participationList, List<Certification> certificationList, Long savedMoney, Long reward, String kakaoId, String kakaoName) {
        this.username = username;
        this.participationList = participationList;
        this.certificationList = certificationList;
        this.savedMoney = savedMoney;
        this.reward = reward;
        this.kakaoId = kakaoId;
        this.kakaoName = kakaoName;
    }

    public void addParticipation(Participation participation) {
        if (this.participationList == null){
            this.participationList = new ArrayList<Participation>();
        }

        // 이미 가입한 경우 예외 처리
        if (isAlreadyJoined(participation)) {
            throw new AlreadyJoinedException("이미 신청이 완료된 챌린지입니다.");
        }

        this.participationList.add(participation);
    }

    private boolean isAlreadyJoined(Participation participation) {
        ListIterator<Participation> iter = this.participationList.listIterator();

        while(iter.hasNext()){
            Participation part = iter.next();
            if (part.getChallengeId().equals(participation.getChallengeId())){
                return true;
            }
        }
        return false;
    }

    public void addCertification(Long challengeId, CertInfo certInfo, Long savedMoney, Long reward){

        this.savedMoney += savedMoney;
        this.reward += reward;

        if (this.certificationList == null){ // 추후에 default 값을 두고 없애도 될 듯
            this.certificationList = new ArrayList<Certification>();
        }

        ListIterator<Certification> iter = this.certificationList.listIterator();

        while(iter.hasNext()){
            Certification certification = iter.next();
            if (certification.getChallenge_id().equals(challengeId)){
                certification.addCert(certInfo);
                return;
            }
        }

        // 해당 챌린지 인증이 처음인 경우
        Certification newCertification = Certification.builder()
                .id(challengeId)
                .cert(new ArrayList<CertInfo>())
                .build();
        newCertification.addCert(certInfo);
        this.certificationList.add(newCertification);
    }

    public boolean isMaxCertification(Long challengeId, Long maxCnt) {
        ListIterator<Certification> iter = this.certificationList.listIterator();

        while(iter.hasNext()){
            Certification certification = iter.next();
            if (certification.getChallenge_id().equals(challengeId)){
                long cnt = 0;
                for (CertInfo certInfo: certification.getCert()){
                    LocalDate currentDate = LocalDate.now();
                    LocalDate dateFromTimestamp = certInfo.getDate().toLocalDateTime().toLocalDate();
                    if (currentDate.isEqual(dateFromTimestamp)){
                        cnt++;
                    }
                }

                if (cnt >= maxCnt) return true;
                else return false;
            }
        }
        return false;
    }
}