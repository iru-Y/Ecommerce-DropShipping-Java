package com.delivery.trizi.trizi.controllers;

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

import java.net.SocketException;
import java.net.UnknownHostException;

@AllArgsConstructor
@Log4j2
@RestController
@RequestMapping(value = "/products")
public class ProductController {
    final private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductModel>> getAllProducts(Pageable pageable) {
        Page<ProductModel> products = productService.getAll(pageable);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProductModel> getByUserID(@PathVariable String userId) throws UnknownHostException {
        ProductModel productModel = productService.getById(userId);
        if (productModel != null) {
            String profileImageUrl = productModel.getProductImage();
            productModel.setProductImage(profileImageUrl);

            return ResponseEntity.ok().body(productModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductModel> post(@RequestParam("user") String userJson,
                                          @RequestParam("file") MultipartFile file) throws UnknownHostException, SocketException {
        var userDto = new Gson().fromJson(userJson, ProductModel.class);

        ProductModel savedUser = productService.post(userDto);
        ProductModel imageLink = productService.post(savedUser.getId(), file);
        savedUser.setProductImage(imageLink.getProductImage());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductModel> updateUser(@PathVariable String id,
                                                @RequestParam("user") String userJson,
                                                @RequestParam("file") MultipartFile file) throws UnknownHostException, SocketException {
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(userJson, productModel);
        ProductModel updatedUser = productService.put(id, productModel, file);
        ProductModel imageLink = productService.post(id, file);
        updatedUser.setProductImage(imageLink.getProductImage());

        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        productService.delete(userId);
        return ResponseEntity.ok().body("Usuário excluído com sucesso.");
    }

}
