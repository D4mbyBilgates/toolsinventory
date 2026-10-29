package toolsinventory;


import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author obana
 */
public class PasswordUtils {
    
     private static final int ITERATIONS = 600000;
    private static final int SALT_LENGTH = 16;
    private static final int KEY_LENGTH = 256;

    public static String hashPassword(String password) {

        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);

        byte[] hash = generateHash(password, salt);

        return "PBKDF2$" + ITERATIONS + "$"
                + Base64.getEncoder().encodeToString(salt) + "$"
                + Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verifyPassword(
            String password, String storedPassword) {

        try {
            String[] parts = storedPassword.split("\\$");

            if (parts.length != 4
                    || !parts[0].equals("PBKDF2")) {
                return false;
            }

            int iterations = Integer.parseInt(parts[1]);

            if (iterations < 100000 || iterations > 1000000) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] storedHash = Base64.getDecoder().decode(parts[3]);

            if (salt.length != SALT_LENGTH
                    || storedHash.length != KEY_LENGTH / 8) {
                return false;
            }

            byte[] enteredHash =
                    generateHash(password, salt, iterations);

            int difference = 0;

            for (int i = 0; i < storedHash.length; i++) {
                difference |= storedHash[i] ^ enteredHash[i];
            }

            return difference == 0;

        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] generateHash(
            String password, byte[] salt) {

        return generateHash(password, salt, ITERATIONS);
    }

    private static byte[] generateHash(
            String password, byte[] salt, int iterations) {

        try {
            KeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    iterations,
                    KEY_LENGTH
            );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256");

            return factory.generateSecret(spec).getEncoded();

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Unable to hash password.", e);
        }
    }
    
}
