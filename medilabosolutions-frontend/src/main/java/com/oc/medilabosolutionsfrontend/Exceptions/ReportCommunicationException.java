package com.oc.medilabosolutionsfrontend.Exceptions;

public class ReportCommunicationException extends RuntimeException {

    private final String errorMessage;

    public ReportCommunicationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}