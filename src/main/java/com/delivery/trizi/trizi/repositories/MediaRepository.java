package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.domain.media.MediaModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface MediaRepository extends MongoRepository<MediaModel, String> {

   Optional<MediaModel> getImageByTitle(String title);

}
