package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.infra.security.domain.RoleSecurity;

public interface MongoCrudImpl {
    RoleSecurity post(RoleSecurity roleSecurity);
    void delete (String id);
}
