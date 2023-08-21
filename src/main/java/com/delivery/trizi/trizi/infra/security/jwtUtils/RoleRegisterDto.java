package com.delivery.trizi.trizi.infra.security.jwtUtils;

public record RoleRegisterDto(String login, String password, RoleEnum role) {
}
