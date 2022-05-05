package com.example.javaapinethits.utilities;

import com.example.javaapinethits.model.User;
import com.github.curiousoddman.rgxgen.RgxGen;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Token {
    private static final String PATTERN = "^[a-zA-Z0-9]{16}$";

    /**
     * Validates the token according to the users in the database
     * @param token The token to validate
     * @param users The users in the database
     * @return If the token is valid, or throws a ResponseStatusException if not
     */
    public static boolean validateToken(
            String token,
            List<User> users
    ) {
        boolean isValid = false;
        token = getTokenFromRequest(token);
        if (!token.matches(getPattern()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Date now = new Date();
        for (User user : users) {
            if (Objects.equals(user.getToken(), token))
                if (user.getToken_expiration().after(now))
                    return true;
                else
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Generates a new token based on the REGEX pattern
     * @return The new token
     */
    public static String generateToken() {
        RgxGen rgxGen = new RgxGen(getPattern());
        return rgxGen.generate();
    }

    /**
     * Gets the REGEX pattern for the token
     * @return The REGEX pattern
     */
    public static String getPattern() {
        return PATTERN;
    }

    private static String getTokenFromRequest(String token) {
        if (token.matches(getPattern()))
            return token;
        token = token.replace("\n", "");
        token = token.replace("\r", "");
        token = token.replace("\t", "");
        token = token.replace("{", "");
        token = token.replace("}", "");
        token = token.replace("\"token\"", "");
        token = token.replace(":", "");
        token = token.replace("\"", "");
        token = token.replace(" ", "");
        return token;
    }
}
