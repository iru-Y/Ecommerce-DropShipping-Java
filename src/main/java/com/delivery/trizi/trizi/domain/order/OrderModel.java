package com.delivery.trizi.trizi.domain.order;

import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.domain.user.UserModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Document(value = "ORDERS")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderModel implements Serializable {
    @Id
    private String id;
    private String date;
    private String tracker;
    private OrderStatusEnum status;
    private UserModel userModel;
    private List<ProductModel> productModelList = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        generateTracker();
    }

    private void generateTracker() {
        var prefix = "Monkey";
        var suffix = "777";
        var random = new Random();
        long randomNum = random.nextLong(9000000) + 1000000;
        this.tracker = prefix + randomNum + suffix;
    }
}
