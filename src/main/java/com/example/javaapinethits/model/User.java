package com.example.javaapinethits.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import static com.example.javaapinethits.utilities.Password.generatePassword;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String hashed_password;
    private String token;
    private Date token_expiration;

    public User(int id, String username, String password, String token, String token_expiration) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.id = id;
        setUsername(username);
        setPassword(generatePassword(password));
        setToken(token);
        setToken_expiration(new Date(token_expiration));
    }

    public User() {
        setUsername("");
        setPassword("");
        setToken("");
        setToken_expiration();
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return hashed_password;
    }

    public void setPassword(String hashed_password) {
        this.hashed_password = hashed_password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getToken_expiration() {
        return token_expiration;
    }

    public void setToken_expiration() {
        Date today = new Date();
        this.token_expiration = new Date(today.getTime() + (1000 * 60 * 60 * 24));
    }

    public void setToken_expiration(Date token_expiration) {
        this.token_expiration = token_expiration;
    }
}
