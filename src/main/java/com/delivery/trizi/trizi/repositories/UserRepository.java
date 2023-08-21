package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.domain.user.User;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByLogin(String login);

}
