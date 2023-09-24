package com.delivery.trizi.trizi.domain.order;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    DELIVERED("DELIVERED"),
    SHIPPED ("SHIPPED");
    private final String status;

    OrderStatusEnum(String status) {
        this.status = status;
    }
}
