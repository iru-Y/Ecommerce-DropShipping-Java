package com.delivery.trizi.trizi.domain.user;

import com.delivery.trizi.trizi.infra.security.jwtUtils.RoleEnum;

public record UserDto(String login, String password, String mail, RoleEnum role) {
}
