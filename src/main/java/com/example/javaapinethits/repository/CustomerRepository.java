package com.example.javaapinethits.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.javaapinethits.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
