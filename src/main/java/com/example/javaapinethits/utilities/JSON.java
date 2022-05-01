package com.example.javaapinethits.utilities;

import com.example.javaapinethits.model.Customer;
import com.example.javaapinethits.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JSON {
    /**
     * Converts to a JSON string the User object.
     * @param u The User object to convert.
     * @return The JSON string.
     */
    public static String toJSON(@NotNull User u) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(u);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts to a JSON string the Customer object.
     * @param c The Customer object to convert.
     * @return The JSON string.
     */
    public static String toJSON(@NotNull Customer c) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(c);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts to a JSON string the list of Customer objects.
     * @param cl The list of Customer objects to convert.
     * @return The JSON string.
     */
    public static String toJSON(@NotNull List<Customer> cl) {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder json = new StringBuilder();
        json.append("{\"customers\": [");
        try {
            for (Customer c : cl) {
                json.append(mapper.writeValueAsString(c));
                json.append(",");
            }
            json.setLength(Math.max(json.length() - 1, 0));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json.append("]}").toString();
    }
}
