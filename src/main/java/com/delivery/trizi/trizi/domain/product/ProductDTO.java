package com.delivery.trizi.trizi.domain.product;

import java.io.Serializable;

public record ProductDTO(
        String description,
        Long quantity,
        String price,
        String mediaModel) implements Serializable {
}
