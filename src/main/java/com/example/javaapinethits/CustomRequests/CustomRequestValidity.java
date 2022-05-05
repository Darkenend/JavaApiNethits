package com.example.javaapinethits.CustomRequests;

import java.util.List;

public class CustomRequestValidity {
    private boolean isValid = true;
    private List<String> invalidParameters;
    private String message;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public List<String> getInvalidParameters() {
        return invalidParameters;
    }

    public void setInvalidParameters(List<String> invalidParameters) {
        this.invalidParameters = invalidParameters;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
