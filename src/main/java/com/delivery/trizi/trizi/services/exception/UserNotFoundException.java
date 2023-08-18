package com.delivery.trizi.trizi.services.exception;

public class UserNotFoundException extends RuntimeException{

    private String message;
    public UserNotFoundException() {
        this.message = "Usuário não é um objeto válido!";
    }
}
