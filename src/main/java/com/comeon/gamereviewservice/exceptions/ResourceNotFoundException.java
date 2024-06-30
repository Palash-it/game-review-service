package com.comeon.gamereviewservice.exceptions;

import jakarta.persistence.PersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends PersistenceException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
