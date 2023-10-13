package com.delivery.trizi.trizi.utils;

import com.delivery.trizi.trizi.domain.order.OrderModel;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OrderListeners extends AbstractMongoEventListener<OrderModel> {
    private final MongoOperations mongoOperations;
    public OrderListeners(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<OrderModel> event) {
        OrderModel order = event.getSource();
        if (order.getTracker() == null || order.getTracker().isEmpty()) {
            order.setTracker(generateTracker());
        }
        if (order.getCreatedAt() == null || order.getCreatedAt().isEmpty()) {
            order.setCreatedAt(GetHour.getHour());
        }else {
            order.setUpdatedAt(GetHour.getHour());
        }
    }
    private String generateTracker() {
        var prefix = "Monkey";
        var suffix = "777";
        var random = new Random();
        long randomNum = random.nextLong(9000000);
        return prefix + randomNum + suffix;
    }
}
