package com.musalasoft.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
public class DroneApiException extends RuntimeException {
    private final HttpStatus error;

    public DroneApiException(HttpStatus error, String message) {
        this(error, message, null);
    }

    public DroneApiException(HttpStatus error, String message, Throwable t) {
        super(message, t);
        this.error = error;
    }
}
