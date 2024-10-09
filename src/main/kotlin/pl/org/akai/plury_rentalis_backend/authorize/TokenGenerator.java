package pl.org.akai.plury_rentalis_backend.authorize;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TokenGenerator {
    private TokenGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateToken(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

//    public static String decodeToken(String token) {
//        byte[] decodedBytes = Base64.getDecoder().decode(token);
//        return new String(decodedBytes);
//    }
//
//    public static String encodeToken(String token) {
//        byte[] decodedBytes = Base64.getEncoder().encode(token.getBytes());
//        return new String(decodedBytes);
//    }
//    public static String decodeToken(AuthDataModel authDataModel) {
//        return decodeToken(authDataModel.token());
//    }
//
//    public static void main(String[] args) {
//        String input = "analfabeta";
//        String token = encodeToken(input);
//        System.out.println("Generated Token: " + token);
//
//        String originalInput = decodeToken(token);
//        System.out.println("Original Input: " + originalInput);
//    }
}
