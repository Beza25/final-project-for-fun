package com.example.demo.business.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i]
                    & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }
    public static String md5Hex (String message) {
        try {
            MessageDigest md =
                    MessageDigest.getInstance("MD5");
            return hex (md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }
}
//public class MD5Util {
//    private static String hex(byte[] array) {
//        var sb = new StringBuilder();
//        for (var byt : array) {
//            sb.append(Integer.toHexString((byt & 0xFF) | 0x100), 1, 3);
//        }
//        return sb.toString();
//    }
//
//    public static String md5Hex(String message) {
//        try {
//            var md = MessageDigest.getInstance("MD5");
//            return hex(md.digest(message.getBytes("CP1252")));
//        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}