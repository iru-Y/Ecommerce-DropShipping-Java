package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.media.MediaModel;
import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.services.MediaService;
import com.delivery.trizi.trizi.services.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    private ResponseEntity<List<ProductModel>> getAll () {
        log.info("Entrando no getAll product");
        var productList = productService.getAll();
        var products = productList.stream().map(x-> {
                    String id = x.getId();
                    return x.add(linkTo(methodOn(ProductController.class).getProduct(id)).withSelfRel());
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductModel> getProduct(@PathVariable String id) {
        ProductModel productModel = productService.getById(id);


        return ResponseEntity.ok().body(productModel);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductModel> createProductWithMedia(
            @RequestBody ProductModel productModel) {
        if (productModel.getMediaModel() != null && productModel.getMediaModel().getId() != null) {
            MediaModel mediaModel = mediaService.getMedia(productModel.getMediaModel().getId());
            productModel.setMediaModel(mediaModel);
            log.info("request Media ok");
        }

        ProductModel createdProductModel = productService.post(productModel);
        log.info("produto criado");
        return ResponseEntity.ok(createdProductModel);

    }
}
