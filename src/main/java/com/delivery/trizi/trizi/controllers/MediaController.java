package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.media.Media;
import com.delivery.trizi.trizi.services.MediaService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping("/media/product/{id}")
    public Media getProductMedia(@PathVariable String id, Model model) {
        Media media = mediaService.getMedia(id);
        model.addAttribute("title", media.getTitle());
        model.addAttribute("media",
                Base64.getEncoder().encodeToString(media.getData().getData()));
        return media;
    }
    @PostMapping("/media/product")
    public String postProductMedia(@RequestParam("title") String title,
                                   @RequestParam("media") MultipartFile image)
            throws IOException {
        String id = mediaService.addMedia(title, image);
        return "redirect:/media/" + id;
    }
}
