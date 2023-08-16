package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.domain.user.User;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByLogin(String login);

}
