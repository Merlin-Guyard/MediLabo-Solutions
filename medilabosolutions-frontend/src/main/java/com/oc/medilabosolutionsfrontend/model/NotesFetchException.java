package com.oc.medilabosolutionsfrontend.model;

public class NotesFetchException extends RuntimeException {

    private final String errorMessage;

    public NotesFetchException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}