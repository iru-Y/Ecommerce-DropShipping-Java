package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.domain.user.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
    UserModel findByMail(String mail);

}
