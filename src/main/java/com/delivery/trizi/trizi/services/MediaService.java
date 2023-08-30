package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.media.MediaModel;
import com.delivery.trizi.trizi.repositories.MediaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public Page<MediaModel> getAllMedia(Pageable pageable) {
        return mediaRepository.findAll(pageable);
    }

    public String saveImage(MultipartFile file, String title) {
        try {
            byte[] imageData = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageData);

            MediaModel mediaModel = new MediaModel();
            mediaModel.setData(base64Image);
            mediaModel.setTitle(title);
            MediaModel savedMedia = mediaRepository.save(mediaModel);
            return savedMedia.getId();
        } catch (IOException e) {
            return null;
        }
    }

    public byte[] getImageById(String mediaId) {
        Optional<MediaModel> optionalMediaModel = mediaRepository.findById(mediaId);
        if (optionalMediaModel.isPresent()) {
            String base64Image = optionalMediaModel.get().getData();
            return Base64.getDecoder().decode(base64Image);
        }
        return null;
    }
    public byte[] getImageByTitle(String title) {
        Optional<MediaModel> optionalMediaModel = mediaRepository.getImageByTitle(title);
        if (optionalMediaModel.isPresent()) {
            String base64Image = optionalMediaModel.get().getData();
            return Base64.getDecoder().decode(base64Image);
        }
        return null;
    }

    public boolean deleteMediaById(String mediaId) {
        if (mediaRepository.existsById(mediaId)) {
            mediaRepository.deleteById(mediaId);
            return true;
        }
        return false;
    }

    public boolean patchMedia(String mediaId, Map<String, String> patchData) {
        Optional<MediaModel> optionalMediaModel = mediaRepository.findById(mediaId);
        if (optionalMediaModel.isPresent()) {
            MediaModel mediaModel = optionalMediaModel.get();
            if (patchData.containsKey("title")) {
                mediaModel.setTitle(patchData.get("title"));
            }
            mediaRepository.save(mediaModel);
            return true;
        }
        return false;
    }
}
