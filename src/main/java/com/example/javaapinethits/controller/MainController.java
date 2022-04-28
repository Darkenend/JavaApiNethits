package com.example.javaapinethits.controller;

import com.example.javaapinethits.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping(path="/customer")
    public @ResponseBody String addNewCustomer (
            @RequestParam String token,
            @RequestParam String name,
            @RequestParam String phone) {

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
            @RequestParam String token,
            @RequestParam String username,
            @RequestParam String password
    ) {
        return "JSON with Token";
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
}
