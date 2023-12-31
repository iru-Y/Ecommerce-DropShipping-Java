package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.product.ProductDTO;
import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.services.ProductService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ProductModel> getByUserID(@PathVariable String id) {
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
    public ResponseEntity<ProductModel> post(   @RequestParam("product") String product,
                                                @RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.post(product, file));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductModel> updateProduct(@PathVariable String id,
                                                      @RequestParam("product") String product,
                                                      @RequestParam("file") MultipartFile file) {
        var productModel = new Gson().fromJson(product, ProductModel.class);
        ProductModel updatedProduct = productService.put(id, productModel, file);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.ok().body("Usuário excluído com sucesso.");
    }
}
