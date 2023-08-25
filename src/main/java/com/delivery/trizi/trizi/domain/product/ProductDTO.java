package com.delivery.trizi.trizi.domain.product;

import com.delivery.trizi.trizi.domain.media.MediaModel;

import java.io.Serializable;

public record ProductDTO(String description,Long quantity, String price, MediaModel mediaModel) implements Serializable {
}
