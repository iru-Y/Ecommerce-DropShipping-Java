package com.delivery.trizi.trizi.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;

@Document(collection = "PRODUCTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel implements Serializable {
    @Id
    private String id;
    private String description;
    private Long quantity;
    private Double price;
    private String productImage;
}
