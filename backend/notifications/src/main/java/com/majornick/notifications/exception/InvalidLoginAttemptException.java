package com.majornick.notifications.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvalidLoginAttemptException extends RuntimeException {
    private final String message;
}
