package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.domain.media.MediaModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MediaRepository extends MongoRepository<MediaModel, String> {
}
