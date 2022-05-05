package com.example.javaapinethits.CustomRequests;

import java.util.ArrayList;
import java.util.List;

public class NewCustomerRequest implements CustomRequest{
    private String token;
    private String name;
    private String phone;

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }


    @Override
    public CustomRequestValidity isValidCustomRequest() {
        CustomRequestValidity validity = new CustomRequestValidity();
        List<String> invalidParameters = new ArrayList<>();
        if (getToken() == null)
            invalidParameters.add("token");
        if (getName() == null)
            invalidParameters.add("name");
        if (getPhone() == null)
            invalidParameters.add("phone");
        if (invalidParameters.size() > 0) {
            validity.setValid(false);
            validity.setInvalidParameters(invalidParameters);
            validity.setMessage("Invalid parameters: " + invalidParameters);
        }
        return validity;
    }
}
