package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.domain.user.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
    UserModel findByLogin(String login);

}
