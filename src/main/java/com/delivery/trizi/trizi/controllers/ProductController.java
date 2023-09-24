package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.product.ProductDTO;
import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.UnknownHostException;

@AllArgsConstructor
@Log4j2
@RestController
@RequestMapping(value = "/products")
public class ProductController {
    final private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductModel>> getAllProducts(Pageable pageable) {
        log.info("Entrou no gelAllProducts");
        Page<ProductModel> products = productService.getAllPageable(pageable);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> getByUserID(@PathVariable String id) throws UnknownHostException {
        ProductModel productModel = productService.getById(id);
        if (productModel != null) {
            String profileImageUrl = productModel.getProductImage();
            productModel.setProductImage(profileImageUrl);
            log.info("Entrando no getProducts by id");
            return ResponseEntity.ok().body(productModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductModel> post(@RequestParam("product") String userJson,
                                                @RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.post(userJson, file));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductModel> updateUser(@PathVariable String id,
                                                @RequestParam("product") String userJson,
                                                @RequestParam("file") MultipartFile file) throws IOException {
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(userJson, productModel);
        ProductModel updatedUser = productService.put(id, productModel, file);
        ProductModel imageLink = productService.post(id, file);
        updatedUser.setProductImage(imageLink.getProductImage());
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.ok().body("Usuário excluído com sucesso.");
    }

    @PutMapping(value = "/quantity")
    public ResponseEntity<ProductModel> incrementOrDecrementQuantity(@RequestBody ProductDTO productDTO) {
        ProductModel updatedProduct = productService.incrementOrDecrementQuantity(productDTO.description(), productDTO.quantity());
        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProduct);
    }
}
