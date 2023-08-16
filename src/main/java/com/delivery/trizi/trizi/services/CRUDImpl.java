package com.delivery.trizi.trizi.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CRUDImpl {
    Flux<?> getAll();

    Mono<?> getById(String id);

    Mono<?> post (Object object);

}
