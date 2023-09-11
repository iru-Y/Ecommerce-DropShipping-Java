package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.domain.user.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModel, String> {
    UserModel findByLogin(String login);
}
