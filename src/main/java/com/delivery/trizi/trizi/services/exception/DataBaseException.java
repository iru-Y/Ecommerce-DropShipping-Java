package com.delivery.trizi.trizi.services.exception;

import lombok.AllArgsConstructor;

public class DataBaseException extends RuntimeException{
    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
