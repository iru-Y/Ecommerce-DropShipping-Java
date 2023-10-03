package com.delivery.trizi.trizi.domain.order;

import org.joda.time.DateTime;

public record OrderDto(DateTime date, String tracker, OrderStatusEnum status) {
}
