package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.product.ProductDTO;
import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@Log4j2
@RestController
@RequestMapping(value = "/products")
public class ProductController {
    final private ProductService productService;

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
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productDTO, productModel);
        return ResponseEntity.ok(productService.post(productModel));
    }

}
