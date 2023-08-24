package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.media.Media;
import com.delivery.trizi.trizi.domain.product.Product;
import com.delivery.trizi.trizi.services.MediaService;
import com.delivery.trizi.trizi.services.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Log4j2
@RestController
public class ProductController {
    final private ProductService productService;
    final private MediaService mediaService;

    public ProductController(ProductService productService, MediaService mediaService) {
        this.productService = productService;
        this.mediaService = mediaService;
    }

    @GetMapping("/product")
    private ResponseEntity<List<Product>> getAll () {
        return ResponseEntity.ok().body(productService.getAll());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        Product product = productService.getById(id);


        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProductWithMedia(
            @RequestBody Product product) {
        if (product.getMedia() != null && product.getMedia().getId() != null) {
            Media media = mediaService.getMedia(product.getMedia().getId());
            product.setMedia(media);
            log.info("request Media ok");
        }

        Product createdProduct = productService.post(product);
        log.info("vai toma no cu disgraçaaaaaaaaaaaaaaaaaaaaaa sou picka");
        return ResponseEntity.ok(createdProduct);

    }
}
