package com.oc.medilabosolutionsreport.Exceptions;

public class FetchingKeywordsException extends RuntimeException {

    private final String errorMessage;

    public FetchingKeywordsException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}