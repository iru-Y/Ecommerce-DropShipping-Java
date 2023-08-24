package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.product.Product;
import com.delivery.trizi.trizi.repositories.ProductRepository;
import com.delivery.trizi.trizi.services.exception.DataBaseException;
import com.delivery.trizi.trizi.services.exception.WrongObjectException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements Serializable {

    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll () {
        try {
            return productRepository.findAll();
        } catch (RuntimeException e) {
            throw new DataBaseException("Erro ao obter dados dos usuários", e);
        }

    }
    
    public Product post (@RequestBody Product product) {
        if (product == null) {
            throw new WrongObjectException("O produto não pode estar nulo");
        }
        return productRepository.save(product);
    }
}
