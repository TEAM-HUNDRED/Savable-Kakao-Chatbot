package com.management.web.service.dto;

import com.management.web.domain.GiftcardOrder;
import com.management.web.domain.GiftcardProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
public class GiftcardHistoryDto {
    private String productName;
    private Timestamp orderDate;
    private Long quantity;
    private Long price;
    private String image;
    private String status;

    public GiftcardHistoryDto(List<Object> objects) {
        GiftcardOrder giftcardOrder = (GiftcardOrder) objects.get(0);
        GiftcardProduct giftcardProduct = (GiftcardProduct) objects.get(1);

        this.productName=giftcardProduct.getName();
        this.orderDate=giftcardOrder.getDate();
        this.quantity = giftcardOrder.getQuantity();
        this.price = giftcardProduct.getPrice() * quantity;
        this.image = giftcardProduct.getImage();
        this.status = giftcardOrder.getStatus();

    }

}
