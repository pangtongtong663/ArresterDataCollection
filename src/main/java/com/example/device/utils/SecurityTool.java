package com.example.device.utils;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityTool {
    private static MessageDigest md5;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt (String password, String salt) {
        String passwordAndSalt = password + salt;
        return Hex.encodeHexString(md5.digest(passwordAndSalt.getBytes(StandardCharsets.UTF_8)));
    }

    public static boolean match(String encrypted, String unEncrypted, String salt) {

        return encrypted.equals(encrypt(unEncrypted, salt));

    }

}
