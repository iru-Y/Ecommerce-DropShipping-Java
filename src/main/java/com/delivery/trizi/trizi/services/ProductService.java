package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.infra.storage.StorageService;
import com.delivery.trizi.trizi.repositories.ProductRepository;
import com.delivery.trizi.trizi.services.exception.DataBaseException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final StorageService storageService;

    public Page<ProductModel> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public ProductModel getById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new DataBaseException("Produto não encontrado para o id " + id));
    }

    public ProductModel post(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    public ProductModel put(String productId, ProductModel productModel, MultipartFile file) {
        Optional<ProductModel> existingProductOptional = productRepository.findById(productId);

        if (existingProductOptional.isPresent()) {
            ProductModel existingProduct = existingProductOptional.get();
            existingProduct.setPrice(productModel.getPrice());
            existingProduct.setQuantity(productModel.getQuantity());
            existingProduct.setDescription(productModel.getDescription());

            if (file != null && !file.isEmpty()) {
                String imageLink = storageService.uploadFile(file);
                existingProduct.setProductImage(imageLink);
            }
            return productRepository.save(existingProduct);
        }

        return null;
    }

    public boolean delete(String productId) {
        Optional<ProductModel> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            ProductModel product = productOptional.get();
            String productImageLink = product.getProductImage();
            if (productImageLink != null && !productImageLink.isEmpty()) {
                storageService.deleteFile(productImageLink);
            }
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    public ProductModel post(String userJson, MultipartFile file) throws IOException {
        String fileName = storageService.uploadFile(file);
        ProductModel productModel = new Gson().fromJson(userJson, ProductModel.class);
        productModel.setProductImage(storageService.getFileDownloadUrl(fileName));
        return productRepository.save(productModel);
    }
}
