package com.delivery.trizi.trizi.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FluxCRUDImpl {
    Flux<?> getAll();

    Mono<?> getById(String id);

    Mono<?> post (Object object);

}