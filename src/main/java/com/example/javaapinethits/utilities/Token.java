package com.example.javaapinethits.utilities;

import com.example.javaapinethits.model.User;
import com.github.curiousoddman.rgxgen.RgxGen;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Token {
    public static boolean validateToken(
            String token,
            List<User> users) {
        boolean isValid = false;
        if (!token.matches(getPattern())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Date now = new Date();
        for (User user : users) {
            if (Objects.equals(user.getToken(), token)) {
                if (user.getToken_expiration().after(now)) {
                    return true;
                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN);
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public static String generateToken() {
        RgxGen rgxGen = new RgxGen(getPattern());
        return rgxGen.generate();
    }

    public static String getPattern() {
        return "^[0-9a-zA-Z]{16}$";
    }
}
