package com.delivery.trizi.trizi.utils;

import com.delivery.trizi.trizi.domain.order.OrderModel;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Component
public class Listeners extends AbstractMongoEventListener<OrderModel> {

    private final MongoOperations mongoOperations;

    public Listeners(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<OrderModel> event) {
        OrderModel order = event.getSource();

        if (order.getTracker() == null || order.getTracker().isEmpty()) {
            order.setTracker(generateTracker());
        }
        if (order.getDate() == null || order.getDate().isEmpty()) {
            order.setDate(getHour());
        }
    }

    private String generateTracker() {
        var prefix = "Monkey";
        var suffix = "777";
        var random = new Random();
        long randomNum = random.nextLong(9000000) + 1000000;
        return prefix + randomNum + suffix;
    }

    private String getHour () {
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        Instant instant = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z")
                .withZone(zoneId);
        return formatter.format(instant);
    }
}
