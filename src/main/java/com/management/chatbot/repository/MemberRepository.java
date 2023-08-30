package com.management.chatbot.repository;

import com.management.chatbot.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByKakaoId(String kakaoId);
    Member save(Member member);
}
