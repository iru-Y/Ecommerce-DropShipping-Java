package com.delivery.trizi.trizi.domain.order;

import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.domain.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Document(value = "ORDERS")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order implements Serializable {
    @Id
    private String id;
    @DateTimeFormat(pattern = "dd/MM/YY")
    private DateTime date = DateTime.now();
    private String tracker;
    private OrderStatusEnum status;
    private UserModel userModel;
    private List<ProductModel> productModelList = new CopyOnWriteArrayList<>();

}
