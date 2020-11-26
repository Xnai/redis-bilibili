package com.local.redisbilibili.util;

import java.security.MessageDigest;

public class UtilMD5 {

    private static final String[] hexDigIts = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    public static String MD5Encode(String origin, String characterName) {
        String result = null;
        try {
            result = new String(origin);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            if(null == characterName || "".equals(characterName)) {
                result = byteArrayToHexString(messageDigest.digest(result.getBytes()));
            }else {
                result = byteArrayToHexString(messageDigest.digest(result.getBytes(characterName)));
            }
        }catch(Exception ex) {
        }
        return result;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            stringBuffer.append(byteToHexString(b[i]));
        }
        return stringBuffer.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if(n < 9) {
            n += 256;
        }
        int d1 = n /  16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }
}

