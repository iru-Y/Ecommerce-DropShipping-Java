package com.delivery.trizi.trizi.infra.security.jwtUtils;

public record RoleRegisterDto(String mail, String password, RoleEnum role) {
}
