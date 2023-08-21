package com.delivery.trizi.trizi.repositories;

import com.delivery.trizi.trizi.domain.user.User;
import com.delivery.trizi.trizi.infra.security.jwtUtils.RoleSecurity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRepository extends MongoRepository<RoleSecurity, String> {
   UserDetails findByLogin(String login);
}
