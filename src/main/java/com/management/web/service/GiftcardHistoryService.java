package com.management.web.service;

import com.management.web.domain.GiftcardOrder;
import com.management.web.repository.GiftcardOrderRepository;
import com.management.web.service.dto.GiftcardHistoryDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GiftcardHistoryService {
    private final GiftcardOrderRepository giftcardOrderRepository;

    public List<GiftcardHistoryDto> findByKakaoId(String kakaoId){
        return giftcardOrderRepository.findByKakaoId(kakaoId).stream()
                .map(objects -> (new GiftcardHistoryDto(Arrays.stream(objects).toList())))
                .collect(Collectors.toList());
    }
}
