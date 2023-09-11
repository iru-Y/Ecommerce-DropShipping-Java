//package com.delivery.trizi.trizi.services;
//
//import com.delivery.trizi.trizi.domain.product.ProductModel;
//import com.delivery.trizi.trizi.infra.storage.StorageService;
//import com.delivery.trizi.trizi.repositories.ProductRepository;
//import com.delivery.trizi.trizi.services.exception.DataBaseException;
//import com.delivery.trizi.trizi.services.exception.WrongObjectException;
//import com.google.gson.Gson;
//import lombok.AllArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.Serializable;
//import java.util.Optional;
//
//@Log4j2
//@Service
//@AllArgsConstructor
//public class ProductService implements Serializable {
//
//    private final ProductRepository productRepository;
//    private final StorageService storageService;
//
//    public Page<ProductModel> getAll(Pageable pageable) {
//        return productRepository.findAll(pageable);
//    }
//
//    public ProductModel getById(String id) {
//        if (id != null) {
//            return productRepository.findById(id).orElseThrow(
//                    () -> new DataBaseException("Não foi possível encontrar o produto para o id" + id)
//            );
//        }
//        log.info("O ID está nulo");
//        throw new DataBaseException("Por favor o ID não pode ser nulo", new RuntimeException());
//    }
//
//    public ProductModel post(ProductModel productModel) {
//        if (productModel == null) {
//            throw new WrongObjectException("O produto não pode estar nulo");
//        }
//        return productRepository.save(productModel);
//    }
//    public ProductModel post(String userJson, MultipartFile file) {
//        ProductModel productModel = new Gson().fromJson(userJson, ProductModel.class);
//        if (file != null && !file.isEmpty()) {
//            String imageLink = storageService.getFileDownloadUrl(file.getOriginalFilename());
//            productModel.setProductImage(imageLink);
//        }
//        return productRepository.save(productModel);
//    }
//
//    public ProductModel put(String userId, ProductModel productModel, MultipartFile file) {
//        Optional<ProductModel> existingUserOptional = productRepository.findById(userId);
//
//        if (existingUserOptional.isPresent()) {
//            ProductModel existingUser = existingUserOptional.get();
//            existingUser.setPrice(productModel.getPrice());
//            existingUser.setQuantity(productModel.getQuantity());
//            existingUser.setDescription(productModel.getDescription());
//
//            if (file != null && !file.isEmpty()) {
//                String imageLink = storageService.uploadFile(file);
//                existingUser.setProductImage(imageLink);
//            }
//            return productRepository.save(existingUser);
//        }
//
//        return null;
//    }
//
//    public boolean delete(String userId) {
//        Optional<ProductModel> productModel = productRepository.findById(userId);
//
//        if (productModel.isPresent()) {
//            ProductModel user = productModel.get();
//            String profileImageLink = user.getProductImage();
//            if (profileImageLink != null && !profileImageLink.isEmpty()) {
//                storageService.deleteFile(profileImageLink);
//            }
//
//            productRepository.delete(user);
//            return true;
//        }
//        return false;
//    }
//}
