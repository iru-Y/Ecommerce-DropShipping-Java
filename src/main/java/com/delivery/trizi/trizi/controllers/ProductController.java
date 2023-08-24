package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.product.Product;
import com.delivery.trizi.trizi.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    final private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    private ResponseEntity<List<Product>> getAll () {
        return ResponseEntity.ok().body(productService.getAll());
    }

    @PostMapping("/product")
    private ResponseEntity<Product> post (@RequestBody Product product) {
        return ResponseEntity.ok().body(productService.post(product));
    }
}
