package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.domain.media.Media;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MediaRepository extends MongoRepository<Media, String> {
}
