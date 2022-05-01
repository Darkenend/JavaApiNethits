package com.example.javaapinethits.controller;

import com.example.javaapinethits.model.Customer;
import com.example.javaapinethits.model.User;
import com.example.javaapinethits.repository.CustomerRepository;
import com.example.javaapinethits.repository.UserRepository;
import com.example.javaapinethits.utilities.Password;
import com.example.javaapinethits.utilities.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
public class MainController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/customer")
    public @ResponseBody String addNewCustomer (
            @RequestParam String token,
            @RequestParam String name,
            @RequestParam String phone) {
        Customer c = new Customer();
        c.setName(name);
        c.setPhone(phone);
        customerRepository.save(c);
        return "Saved";
    }

    @GetMapping(path="/customer/")
    public @ResponseBody String readAllCustomers(
            @RequestParam String token
            ) {
        return "All Clients";
    }

    @GetMapping(path="/customer/{id}")
    public @ResponseBody String readCustomer(
            @RequestParam String token,
            @RequestParam int id
    ) {
        return "Client with ID:"+id;
    }

    @PostMapping(path="/login")
    public @ResponseBody String login(
            @RequestParam String username,
            @RequestParam String password
    ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User u = new User();
        String token = Token.generateToken();
        u.setUsername(username);
        u.setPassword(handlePasswordGeneration(password));
        u.setToken(token);
        u.setToken_expiration();
        userRepository.save(u);
        return toJSON(u);
    }

    @PutMapping(path = "/customer")
    public @ResponseBody String modifyCustomer(
            @RequestParam String token,
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam String phone
    ) {
        return "Client Modified";
    }

    @DeleteMapping(path = "/customer")
    public @ResponseBody String deleteCustomer(
            @RequestParam String token,
            @RequestParam int id
    ) {
        return "Client Deleted";
    }

    @GetMapping(path = "/darkenend")
    public @ResponseBody String testing() {
        return "Test Connection?";
    }

    private String handlePasswordGeneration(String password) {
        String passwordHashed = "";
        try {
            passwordHashed = Password.generatePassword(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return passwordHashed;
    }

    private String toJSON(@NotNull User u) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(u);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJSON(@NotNull Customer c) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(c);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
