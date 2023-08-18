package com.delivery.trizi.trizi.infra.security.domain;

public record RoleRegisterDto(String login, String password, RoleEnum role) {
}
