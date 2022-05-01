package com.example.javaapinethits.utilities;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class Password {
    /**
     * Hashes the given password with PBKDF2WithHmacSHA1 algorithm.
     * @param passwordToHash The password to hash.
     * @return The hashed password.
     * @throws NoSuchAlgorithmException If the PBKDF2WithHmacSHA1 algorithm is not available.
     * @throws InvalidKeySpecException If the password is too long.
     */
    public static String generatePassword(String passwordToHash) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 777;
        char[] chars = passwordToHash.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);

        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations+":"+toHex(salt)+":"+toHex(hash);
    }

    /**
     * Checks if the given password is correct.
     * @param originalPassword The password to check.
     * @param storedPassword The stored password.
     * @return True if the password is correct, false otherwise.
     * @throws NoSuchAlgorithmException If the PBKDF2WithHmacSHA1 algorithm is not available.
     * @throws InvalidKeySpecException If the password is too long.
     */
    public static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            String[] parts = storedPassword.split(":");
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);

            PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] testHash = skf.generateSecret(spec).getEncoded();

            int diff = hash.length ^ testHash.length;
            for (int i = 0; i < hash.length && i < testHash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }

            return diff == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * Generates a Salt for the Password.
     * @return The generated Salt.
     * @throws NoSuchAlgorithmException If the SHA1PRNG algorithm is not available.
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * Converts a byte array to a hex string.
     * @param array The byte array to convert.
     * @return The hex string.
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    /**
     * Converts a hex string to a byte array.
     * @param hex The hex string.
     * @return The byte array.
     */
    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
