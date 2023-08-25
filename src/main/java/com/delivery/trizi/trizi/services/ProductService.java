package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.repositories.ProductRepository;
import com.delivery.trizi.trizi.services.exception.DataBaseException;
import com.delivery.trizi.trizi.services.exception.WrongObjectException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

@Log4j2
@Service
public class ProductService implements Serializable {

    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductModel> getAll() {
        try {
            return productRepository.findAll();
        } catch (RuntimeException e) {
            throw new DataBaseException("Erro ao obter dados dos usuários", e);
        }

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

    public ProductModel post(@RequestBody ProductModel productModel) {
        if (productModel == null) {
            throw new WrongObjectException("O produto não pode estar nulo");
        }
        return productRepository.save(productModel);
    }
}
