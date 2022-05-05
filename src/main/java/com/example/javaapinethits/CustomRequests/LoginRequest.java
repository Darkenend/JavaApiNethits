package com.example.javaapinethits.CustomRequests;

import java.util.ArrayList;
import java.util.List;

public class LoginRequest implements CustomRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }


    @Override
    public CustomRequestValidity isValidCustomRequest() {
        CustomRequestValidity validity = new CustomRequestValidity();
        List<String> invalidParameters = new ArrayList<>();
        if (getUsername() == null)
            invalidParameters.add("username");
        if (getPassword() == null)
            invalidParameters.add("password");
        if (invalidParameters.size() > 0) {
            validity.setValid(false);
            validity.setInvalidParameters(invalidParameters);
            validity.setMessage("Invalid parameters: " + invalidParameters);
        }
        return validity;
    }
}
