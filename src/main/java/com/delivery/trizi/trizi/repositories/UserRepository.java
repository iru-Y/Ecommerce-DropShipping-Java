package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.domain.user.User;
import com.delivery.trizi.trizi.infra.security.domain.RoleSecurity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends MongoRepository<RoleSecurity, String> {
    Optional<RoleSecurity> findByLogin(String login);

}
