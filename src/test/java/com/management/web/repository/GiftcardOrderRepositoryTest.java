package com.management.web.repository;

import com.management.web.domain.GiftcardOrder;
import com.management.web.domain.GiftcardProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GiftcardOrderRepositoryTest {
    @Autowired
    GiftcardOrderRepository giftcardOrderRepository;

    @Test
    void findAllTest() {
//        List<GiftcardOrder> gitcardOrders = giftcardOrderRepository.findAll();
//        for (GiftcardOrder gitcardOrder : gitcardOrders) {
//            System.out.println("gitcardOrder = " + gitcardOrder);
//        }

        List<Object[]> realAll = giftcardOrderRepository.findJoinedAll();
        for (Object[] objects : realAll) {
            List<Object> list = Arrays.stream(objects).toList();
            GiftcardOrder o = (GiftcardOrder) list.get(0);
            GiftcardProduct o1 = (GiftcardProduct) list.get(1);
            System.out.println("getKakaoId()"+o.getKakaoId());
            System.out.println(o.getGiftcardId() + o1.getPrice());
        }
    }

    @Test
    void findByKakaoIdTest(){
        List<Object[]> giftcards = giftcardOrderRepository.findByKakaoId("e00f5748663955d7c9e74458cb7827746c57adfba485578981328aa637c537e7cc");
        for (Object[] giftcard : giftcards) {
            GiftcardOrder giftcardOrder = (GiftcardOrder) Arrays.stream(giftcard).toList().get(0);
            System.out.println("giftcardOrder = " + giftcardOrder);
            GiftcardProduct giftcardProduct = (GiftcardProduct) Arrays.stream(giftcard).toList().get(1);
            System.out.println("giftcardProduct = " + giftcardProduct);

        }
    }
}
