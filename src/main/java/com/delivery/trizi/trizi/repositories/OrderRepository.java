package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.domain.order.OrderModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderModel, String> {
    OrderModel findByTracker(String tracker);
    boolean existsByTracker(String tracker);
}
