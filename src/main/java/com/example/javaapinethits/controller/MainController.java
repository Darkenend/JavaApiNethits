package com.example.javaapinethits.controller;

import com.example.javaapinethits.model.Customer;
import com.example.javaapinethits.model.User;
import com.example.javaapinethits.repository.CustomerRepository;
import com.example.javaapinethits.repository.UserRepository;
import com.example.javaapinethits.utilities.JSON;
import com.example.javaapinethits.utilities.Password;
import com.example.javaapinethits.utilities.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam String token,
            @RequestParam String name,
            @RequestParam String phone) {
        tokenInput(token);
        Customer c = new Customer();
        c.setName(name);
        c.setPhone(phone);
        customerRepository.save(c);
        return JSON.toJSON(c);
    }

    @GetMapping(path="/customer")
    public @ResponseBody String readAllCustomers(
            @RequestParam String token
            ) {
        tokenInput(token);
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        return JSON.toJSON(customers);
    }

    @GetMapping(path="/customer/{id}")
    public @ResponseBody String readCustomer(
            @RequestParam String token,
            @PathVariable int id) {
        tokenInput(token);
        Customer c = customerRepository.findById(id).get();
        return JSON.toJSON(c);
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
        return JSON.toJSON(u);
    }

    @PutMapping(path = "/customer")
    public @ResponseBody String modifyCustomer(
            @RequestParam String token,
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam String phone
    ) {
        tokenInput(token);
        Customer c = customerRepository.findById(id).get();
        c.setName(name);
        c.setPhone(phone);
        customerRepository.save(c);
        return JSON.toJSON(c);
    }

    @DeleteMapping(path = "/customer")
    public @ResponseBody String deleteCustomer(
            @RequestParam String token,
            @RequestParam int id
    ) {
        tokenInput(token);
        Customer c = customerRepository.findById(id).get();
        customerRepository.delete(c);
        return JSON.toJSON(c);
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

    private void tokenInput(String token) {
        List<User> users = (List<User>) userRepository.findAll();
        Token.validateToken(token, users);
    }
}
