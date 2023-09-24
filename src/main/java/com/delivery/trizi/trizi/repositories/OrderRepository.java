package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.domain.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findByTracker(String tracker);
}
