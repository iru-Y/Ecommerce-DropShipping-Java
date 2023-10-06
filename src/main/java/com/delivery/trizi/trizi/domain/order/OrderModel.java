package com.delivery.trizi.trizi.domain.order;

import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.domain.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(value = "ORDERS")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderModel implements Serializable {

    @Id
    private String id;
    private String createdAt;
    private String updatedAt;
    private String tracker;
    private OrderStatusEnum status;
    private Double total;
    private UserModel userModel;
    private List<ProductModel> productModelList = new ArrayList<>();

}
