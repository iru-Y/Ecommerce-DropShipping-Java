package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.media.MediaModel;
import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.repositories.MediaRepository;
import com.delivery.trizi.trizi.repositories.ProductRepository;
import com.delivery.trizi.trizi.services.exception.DataBaseException;
import com.delivery.trizi.trizi.services.exception.WrongObjectException;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

@Log4j2
@Service
public class ProductService implements Serializable {

    private final ProductRepository productRepository;
    private final MediaRepository mediaRepository;
    public ProductService(ProductRepository productRepository, MediaRepository mediaRepository) {
        this.productRepository = productRepository;
        this.mediaRepository = mediaRepository;
    }


    public Page<ProductModel> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public ProductModel getById(String id) {
        if (id != null) {
            return productRepository.findById(id).orElseThrow(
                    () -> new DataBaseException("Não foi possível encontrar o produto para o id" + id)
            );
        }
        log.info("O ID está nulo");
        throw new DataBaseException("Por favor o ID não pode ser nulo", new RuntimeException());
    }

    public ProductModel post(ProductModel productModel) {
        if (productModel == null) {
            throw new WrongObjectException("O produto não pode estar nulo");
        }
        return productRepository.save(productModel);
    }

    public MediaModel getImageByTitle(String title) {
        Optional<MediaModel> optionalMediaModel = mediaRepository.getImageByTitle(title);
        return optionalMediaModel.orElse(null);
    }
}
