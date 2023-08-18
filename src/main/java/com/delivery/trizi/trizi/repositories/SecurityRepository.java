package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.infra.security.domain.RoleSecurity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository extends MongoRepository<RoleSecurity, String> {
    UserDetails findByLogin(String login);
}
