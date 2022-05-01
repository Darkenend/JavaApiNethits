package com.example.javaapinethits.utilities;

import com.example.javaapinethits.model.User;
import com.github.curiousoddman.rgxgen.RgxGen;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

public class Token {

    public static boolean validateToken(
            String token,
            User user
    ) {
        if (!token.matches(getPattern())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return Objects.equals(user.getToken(), token);
    }

    public static String generateToken() {
        RgxGen rgxGen = new RgxGen(getPattern());
        return rgxGen.generate();
    }

    public static String getPattern() {
        return "^[0-9a-zA-Z]{16}$";
    }
}
