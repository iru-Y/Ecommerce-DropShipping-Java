package com.delivery.trizi.trizi.infra.storage;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StorageRepository extends MongoRepository<StorageModel, String> {
}
