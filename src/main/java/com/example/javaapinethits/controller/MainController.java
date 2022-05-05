package com.example.javaapinethits.controller;

import com.example.javaapinethits.CustomRequests.*;
import com.example.javaapinethits.model.Customer;
import com.example.javaapinethits.model.User;
import com.example.javaapinethits.repository.CustomerRepository;
import com.example.javaapinethits.repository.UserRepository;
import com.example.javaapinethits.utilities.JSON;
import com.example.javaapinethits.utilities.Password;
import com.example.javaapinethits.utilities.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
public class MainController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/customer")
    public @ResponseBody String addNewCustomer (
            @RequestBody NewCustomerRequest newCustomerRequest
    ) {
        checkRequestValidity(newCustomerRequest);
        tokenInput(newCustomerRequest.getToken());
        Customer c = new Customer();
        c.setName(newCustomerRequest.getName());
        c.setPhone(newCustomerRequest.getPhone());
        customerRepository.save(c);
        return JSON.toJSON(c);
    }

    @GetMapping(path="/customer")
    public @ResponseBody String readAllCustomers (
            @RequestBody String token
            ) {
        tokenInput(token);
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        return JSON.toJSON(customers);
    }

    @GetMapping(path="/customer/{id}")
    public @ResponseBody String readCustomer (
            @RequestBody String token,
            @PathVariable int id) {
        tokenInput(token);
        Customer c = customerRepository.findById(id).get();
        return JSON.toJSON(c);
    }

    @PostMapping(path="/login")
    public @ResponseBody String login (
            @RequestBody LoginRequest loginRequest
            ) {
        checkRequestValidity(loginRequest);
        User u = new User();
        String token = Token.generateToken();
        u.setUsername(loginRequest.getUsername());
        u.setPassword(handlePasswordGeneration(loginRequest.getPassword()));
        u.setToken(token);
        u.setToken_expiration();
        userRepository.save(u);
        return JSON.toJSON(u);
    }

    @PutMapping(path = "/customer")
    public @ResponseBody String modifyCustomer (
            @RequestBody PatchCustomerRequest patchCustomerRequest
            ) {
        checkRequestValidity(patchCustomerRequest);
        tokenInput(patchCustomerRequest.getToken());
        Customer c = customerRepository.findById(patchCustomerRequest.getId()).get();
        c.setName(patchCustomerRequest.getName());
        c.setPhone(patchCustomerRequest.getPhone());
        customerRepository.save(c);
        return JSON.toJSON(c);
    }

    @DeleteMapping(path = "/customer")
    public @ResponseBody String deleteCustomer (
            @RequestBody DeleteCustomerRequest deleteCustomerRequest
            ) {
        checkRequestValidity(deleteCustomerRequest);
        tokenInput(deleteCustomerRequest.getToken());
        Customer c = customerRepository.findById(deleteCustomerRequest.getId()).get();
        customerRepository.delete(c);
        return JSON.toJSON(c);
    }

    /**
     * This method is used to handle the password generation from the user's input.
     * @param password The user's input password.
     * @return The hashed password.
     */
    private String handlePasswordGeneration (
            String password
    ) {
        String passwordHashed = "";
        try {
            passwordHashed = Password.generatePassword(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return passwordHashed;
    }

    /**
     * This method is used to check if the token is valid, calling from here the validateToken method in the Token class.
     * @param token The token to be checked.
     */
    private void tokenInput (
            String token
    ) {
        List<User> users = (List<User>) userRepository.findAll();
        Token.validateToken(token, users);
    }

    private void checkRequestValidity (CustomRequest customRequest) {
        CustomRequestValidity crv = customRequest.isValidCustomRequest();
        if (!crv.isValid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, crv.getMessage());
        }
    }
}
