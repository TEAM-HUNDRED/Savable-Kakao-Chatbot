package com.management.web.domain;

public enum GiftcardOrderStatus {
    WAIT("승인대기"),
    READY("발송준비"),
    COMPLETE("발송완료");

    final private String name;
    private GiftcardOrderStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public static GiftcardOrderStatus nameOf(String name) {
        for (GiftcardOrderStatus status : GiftcardOrderStatus.values()) {
            if (status.getName().equals(name)) {
                return status;
            }
        }
        return null;
    }
}
