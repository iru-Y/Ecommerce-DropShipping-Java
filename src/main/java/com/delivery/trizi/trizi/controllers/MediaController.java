package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.media.MediaModel;
import com.delivery.trizi.trizi.services.MediaService;
import com.delivery.trizi.trizi.utils.IpAddressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/media")
public class MediaController {

    private final MediaService mediaService;
    @Autowired
    private IpAddressUtil ipAddressUtil;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping
    public ResponseEntity<Page<MediaModel>> getAllMedia(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) throws UnknownHostException {
        Pageable pageable = PageRequest.of(page, size);
        Page<MediaModel> mediaPage = mediaService.getAllMedia(pageable);

        List<MediaModel> mediaResponses = new ArrayList<>();
        for (MediaModel mediaModel : mediaPage.getContent()) {
            String imageUrl =  ipAddressUtil.getServerUrl() + "/media/id/" + mediaModel.getId();
            MediaModel mediaResponse = new MediaModel(mediaModel.getId(), mediaModel.getTitle(), imageUrl);
            mediaResponses.add(mediaResponse);
        }

        Page<MediaModel> mediaResponsePage = new PageImpl<>(mediaResponses, pageable, mediaPage.getTotalElements());

        return ResponseEntity.ok(mediaResponsePage);
    }

    @GetMapping("/id/{mediaId}")
    public ResponseEntity<ByteArrayResource> downloadMediaById(@PathVariable String mediaId) {
        byte[] imageData = mediaService.getImageById(mediaId);
            ByteArrayResource resource = new ByteArrayResource(imageData);
            return ResponseEntity.ok()
                    .contentLength(imageData.length)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<ByteArrayResource> downloadMedia(@PathVariable String title) {
        byte[] imageData = mediaService.getImageByTitle(title);
        ByteArrayResource resource = new ByteArrayResource(imageData);
        return ResponseEntity.ok()
                .contentLength(imageData.length)
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }

    @PostMapping
    public ResponseEntity<String> uploadMedia(@RequestParam("file") MultipartFile file,
                                              @RequestParam("title") String title) {
        String mediaId = mediaService.saveImage(file, title);
            return ResponseEntity.ok(mediaId);
    }

    @DeleteMapping("/{mediaId}")
    public ResponseEntity<Void> deleteMedia(@PathVariable String mediaId) {
        boolean deleted = mediaService.deleteMediaById(mediaId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{mediaId}")
    public ResponseEntity<Void> patchMedia(@PathVariable String mediaId, @RequestBody Map<String, String> patchData) {
        boolean patched = mediaService.patchMedia(mediaId, patchData);
        if (patched) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
