package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.media.MediaModel;
import com.delivery.trizi.trizi.services.MediaService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@RestController
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping("/media")
    public ResponseEntity<Page<MediaModel>> getAllMedia(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MediaModel> mediaPage = mediaService.getAllMedia(pageable);

        return ResponseEntity.ok(mediaPage);
    }

    @GetMapping("/media/{mediaId}")
    public ResponseEntity<ByteArrayResource> downloadMediaById(@PathVariable String mediaId) {
        byte[] imageData = mediaService.getImageById(mediaId);
            ByteArrayResource resource = new ByteArrayResource(imageData);
            return ResponseEntity.ok()
                    .contentLength(imageData.length)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
    }
    @GetMapping("/media/{title}")
    public ResponseEntity<ByteArrayResource> downloadMedia(@PathVariable String title) {
        byte[] imageData = mediaService.getImageByTitle(title);
        ByteArrayResource resource = new ByteArrayResource(imageData);
        return ResponseEntity.ok()
                .contentLength(imageData.length)
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }

    @PostMapping("/media")
    public ResponseEntity<String> uploadMedia(@RequestParam("file") MultipartFile file,
                                              @RequestParam("title") String title) {
        String mediaId = mediaService.saveImage(file, title);
            return ResponseEntity.ok(mediaId);
    }

    @DeleteMapping("/media/{mediaId}")
    public ResponseEntity<Void> deleteMedia(@PathVariable String mediaId) {
        boolean deleted = mediaService.deleteMediaById(mediaId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/media/{mediaId}")
    public ResponseEntity<Void> patchMedia(@PathVariable String mediaId, @RequestBody Map<String, String> patchData) {
        boolean patched = mediaService.patchMedia(mediaId, patchData);
        if (patched) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
