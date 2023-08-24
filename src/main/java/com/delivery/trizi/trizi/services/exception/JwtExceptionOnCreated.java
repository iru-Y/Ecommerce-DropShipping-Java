package com.delivery.trizi.trizi.services.exception;

public class JwtExceptionOnCreated extends RuntimeException {

    public JwtExceptionOnCreated() {
        String message = "Não foi possível gerar o token";
    }
}
