package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.domain.product.ProductModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<ProductModel, String> {
}
