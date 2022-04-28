package com.example.javaapinethits.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.javaapinethits.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
