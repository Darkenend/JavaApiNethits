package com.example.javaapinethits.CustomRequests;

import java.util.ArrayList;
import java.util.List;

public class DeleteCustomerRequest implements CustomRequest {
    private String token;
    private int id;

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }

    @Override
    public CustomRequestValidity isValidCustomRequest() {
        CustomRequestValidity validity = new CustomRequestValidity();
        List<String> invalidParameters = new ArrayList<>();
        if (getToken() == null)
            invalidParameters.add("token");
        if (getId() < 1)
            invalidParameters.add("id");
        if (invalidParameters.size() > 0) {
            validity.setValid(false);
            validity.setInvalidParameters(invalidParameters);
            validity.setMessage("Invalid parameters: " + invalidParameters);
        }
        return validity;
    }
}
