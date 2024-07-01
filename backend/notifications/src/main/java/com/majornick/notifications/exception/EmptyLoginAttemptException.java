package com.majornick.notifications.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmptyLoginAttemptException extends RuntimeException{
    private final String message;
}
