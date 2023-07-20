package com.management.web.service;

import com.management.chatbot.domain.Member;
import com.management.chatbot.repository.MemberRepository;
import com.management.web.repository.GiftcardRepository;
import com.management.web.service.dto.GiftcardDto;
import com.management.web.service.dto.OrderSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GiftcardService {

    private final GiftcardRepository giftcardRepository;
    private final MemberRepository memberRepository;

    public GiftcardDto findById(Long id) {
        return new GiftcardDto(giftcardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 기프티콘이 없습니다. id=" + id)));
    }

    public List<GiftcardDto> findBySaleYN(Boolean saleYN) {
        return giftcardRepository.findBySaleYN(saleYN).stream()
                .map(GiftcardDto::new)
                .collect(Collectors.toList());
    }

    public List<GiftcardDto> findBySaleYNOrderByPriceAsc(Boolean saleYN) {
        return giftcardRepository.findBySaleYNOrderByPriceAsc(saleYN).stream()
                .map(GiftcardDto::new)
                .collect(Collectors.toList());
    }

    // 기프티콘 구매
    @Transactional
    public Member addOrder(OrderSaveDto orderSaveDto, Long price){
        String kakaoId = orderSaveDto.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId); //동일한 카카오 아이디를 가진 멤버 find
        if (member.getPhoneNumber() == null){ //핸드폰 번호가 등록되어 있지 않은 경우
            member.savePhoneNumber(orderSaveDto.getPhoneNumber());
        }

        member.buyGiftcard(price);

        return member;
    }
}
