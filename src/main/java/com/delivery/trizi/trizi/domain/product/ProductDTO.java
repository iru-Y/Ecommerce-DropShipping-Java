package com.delivery.trizi.trizi.domain.product;

import com.delivery.trizi.trizi.domain.media.Media;

import java.io.Serializable;

public record ProductDTO(String description,Long quantity, String price, Media media) implements Serializable {
}
