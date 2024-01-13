package com.oc.medilabosolutionsfrontend.Exceptions;

public class NoteCommunicationException extends RuntimeException {

    private final String errorMessage;

    public NoteCommunicationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}