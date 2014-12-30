package edu.sysu.lhfcws.mailplus.commons.auth;


import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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
        byte[] raw = messageDigest.digest(CommonUtil.GSON.toJson(object).getBytes());
        String digest = new String(raw);
        try {
            digest = digest.substring(0, 10);
            return digest;
        } catch (StringIndexOutOfBoundsException e) {
            return digest;
        }
    }
}
