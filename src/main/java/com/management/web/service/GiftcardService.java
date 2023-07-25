package com.management.web.service;

import com.management.web.repository.GiftcardProductRepository;
import com.management.web.service.dto.GiftcardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GiftcardService {

    private final GiftcardProductRepository giftcardProductRepository;

    public GiftcardResponseDto findById(Long id) {
        return new GiftcardResponseDto(giftcardProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 기프티콘이 없습니다. id=" + id)));
    }

    public List<GiftcardResponseDto> findBySaleYN(Boolean saleYN) {
        return giftcardProductRepository.findBySaleYN(saleYN).stream()
                .map(GiftcardResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<GiftcardResponseDto> findBySaleYNOrderByPriceAsc(Boolean saleYN) {
        return giftcardProductRepository.findBySaleYNOrderByPriceAsc(saleYN).stream()
                .map(GiftcardResponseDto::new)
                .collect(Collectors.toList());
    }
}
