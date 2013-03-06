package com.dietsodasoftware.isft.xmlrpc.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 3/1/13
 * Time: 3:13 PM
 */
public class DigestionUtils {

    public static String getMD5ForString(String message){
        final MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("This platform doesn't support MD5 digesting.");
        }

        final byte[] hashBytes;
        try {
            hashBytes = md.digest(message.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("This platform doesn't support UTF-8.");
        }

        final StringBuffer hexString = new StringBuffer();
        for (int i=0;i<hashBytes.length;i++) {
            final String hexChar = String.format("%02x", hashBytes[i]);
            hexString.append(hexChar);
        }

        return hexString.toString();
    }
}
