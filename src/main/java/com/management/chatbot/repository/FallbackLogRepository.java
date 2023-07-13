package com.management.chatbot.repository;

import com.management.chatbot.domain.FallbackLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public interface FallbackLogRepository extends JpaRepository<FallbackLog, Long>{
}
