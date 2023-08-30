package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.media.MediaModel;
import com.delivery.trizi.trizi.domain.product.ProductDTO;
import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.services.MediaService;
import com.delivery.trizi.trizi.services.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Log4j2
    @RestController
    @RequestMapping(value = "/products")
    public class ProductController {
    final private ProductService productService;
    private final MediaService mediaService;

    public ProductController(ProductService productService, MediaService mediaService) {
        this.productService = productService;
        this.mediaService = mediaService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductModel>> getAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        log.info("Entrando no getAll product");

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductModel> productsPage = productService.getAll(pageable);

        return ResponseEntity.ok(productsPage);
    }

    @PostMapping
    public ResponseEntity<ProductModel> createProduct(@RequestBody ProductDTO productDTO) {
        String title = productDTO.mediaModel();
        MediaModel mediaModel = productService.getImageByTitle(title);

        if (mediaModel == null) {
            return ResponseEntity.badRequest().build();
        }

        ProductModel productModel = new ProductModel();
        productModel.setDescription(productDTO.description());
        productModel.setQuantity(productDTO.quantity());
        productModel.setPrice(productDTO.price());
        productModel.setMediaModel(mediaModel);

        ProductModel createdProduct = productService.post(productModel);
        return ResponseEntity.ok(createdProduct);
    }

}
