package com.management.web.service;

import com.management.chatbot.domain.Member;
import com.management.chatbot.repository.MemberRepository;
import com.management.web.domain.GiftcardOrder;
import com.management.web.repository.GiftcardOrderRepository;
import com.management.web.service.dto.OrderSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final GiftcardOrderRepository giftcardOrderRepository;
    private final MemberRepository memberRepository;

    // 기프티콘 구매
    @Transactional
    public GiftcardOrder addOrder(OrderSaveRequestDto orderSaveRequestDto, Long price){
        String kakaoId = orderSaveRequestDto.getKakaoId();
        Member member = memberRepository.findByKakaoId(kakaoId); //동일한 카카오 아이디를 가진 멤버 find
        if (member.getPhoneNumber() == null){ //핸드폰 번호가 등록되어 있지 않은 경우
            member.savePhoneNumber(orderSaveRequestDto.getPhoneNumber());
        }
        member.buyGiftcard(price); // 구매 금액만큼 리워드 차감

        GiftcardOrder save = giftcardOrderRepository.save(orderSaveRequestDto.toEntity());// 구매 내역 저장

        return save;
    }
}
