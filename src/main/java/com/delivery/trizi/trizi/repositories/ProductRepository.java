package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.domain.product.ProductModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends MongoRepository<ProductModel, String> {
    List<ProductModel> findByDescription (String description);
}
