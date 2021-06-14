package com.keldranase.expencetrackingapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General type of exception, if something goes wrong with request
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EtBadRequestException extends RuntimeException {

    public EtBadRequestException(String message) {
        super(message);
    }
}
