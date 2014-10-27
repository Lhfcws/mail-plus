package edu.sysu.lhfcws.mailplus.commons.auth;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * An object that generates unique codes for connecting authentication.
 * @author lhfcws
 * @time 14-10-21.
 */
public class AuthCodeGenerator {

    private static MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate a unique code for authentication.
     * @return String auth code
     */
    public String generateCode() {
        byte[] raw = messageDigest.digest(String.valueOf(System.currentTimeMillis()).getBytes());
        return String.valueOf(raw).substring(0, 10);
    }

    /**
     * Generate a unique code for authentication according to a given string.
     * @param object
     * @return String auth code
     */
    public String generateCode(AuthObject object) {
        byte[] raw = messageDigest.digest(String.valueOf(object).getBytes());
        return String.valueOf(raw).substring(0, 10);
    }
}
