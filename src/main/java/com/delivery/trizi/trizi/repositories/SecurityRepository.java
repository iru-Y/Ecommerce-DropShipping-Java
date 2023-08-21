package com.delivery.trizi.trizi.repositories;
import com.delivery.trizi.trizi.infra.security.jwtUtils.RoleSecurity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SecurityRepository extends CrudRepository<RoleSecurity, UUID> {
   RoleSecurity findByLogin(String login);
}
