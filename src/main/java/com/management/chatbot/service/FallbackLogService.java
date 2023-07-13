package com.management.chatbot.service;

import com.management.chatbot.repository.FallbackLogRepository;
import com.management.chatbot.service.dto.FallbackLogSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FallbackLogService {

    private final FallbackLogRepository fallbackLogRepository;

    @Transactional
    public Long save(FallbackLogSaveRequestDto fallbackLogRequestDto) {
        return fallbackLogRepository.save(fallbackLogRequestDto.toEntity()).getId();
    }
}
