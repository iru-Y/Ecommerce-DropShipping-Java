//package com.delivery.trizi.trizi.controllers;
//
//import com.delivery.trizi.trizi.domain.product.ProductDTO;
//import com.delivery.trizi.trizi.domain.product.ProductModel;
//import com.delivery.trizi.trizi.domain.user.UserModel;
//import com.delivery.trizi.trizi.services.ProductService;
//import lombok.AllArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.BeanUtils;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@AllArgsConstructor
//@Log4j2
//@RestController
//@RequestMapping(value = "/products")
//public class ProductController {
//    final private ProductService productService;
//
//    @GetMapping
//    public ResponseEntity<Page<ProductModel>> getAll(@RequestParam(defaultValue = "0") int page,
//                                                     @RequestParam(defaultValue = "10") int size) {
//        log.info("Entrando no getAll product");
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<ProductModel> productsPage = productService.getAll(pageable);
//        return ResponseEntity.ok(productsPage);
//    }
//
//    @PostMapping
//    public ResponseEntity<ProductModel> post(@RequestParam("productJson") String userJson,
//                                             @RequestParam("file") MultipartFile file) {
//        ProductModel userModel = new ProductModel();
//        BeanUtils.copyProperties(userJson, userModel);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(productService.post(userJson, file));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ProductModel> put(@PathVariable String id,
//                                                   @RequestParam("productJson") String userJson,
//                                                   @RequestParam("file") MultipartFile file) {
//        ProductModel productModel = new ProductModel();
//        BeanUtils.copyProperties(userJson, productModel);
//        ProductModel updatedUser = productService.put(id, productModel, file);
//        return ResponseEntity.ok().body(updatedUser);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> delete(@PathVariable String id) {
//        productService.delete(id);
//        return ResponseEntity.ok().body("Usuário excluído com sucesso.");
//    }
//
//}
