package com.audible.exception;

import java.io.Serial;

public class AudibleException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public AudibleException(String message) {
        super(message);
    }
}
