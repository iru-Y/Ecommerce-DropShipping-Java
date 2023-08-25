package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.media.MediaModel;
import com.delivery.trizi.trizi.repositories.MediaRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public String addMedia(String title, MultipartFile file) throws IOException {
      MediaModel mediaModel = new MediaModel(title);
        mediaModel.setData(
                new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        mediaModel = mediaRepository.insert(mediaModel);
        return mediaModel.getId();
    }

    public MediaModel getMedia(String id) {
        return mediaRepository.findById(id).get();
    }
}
