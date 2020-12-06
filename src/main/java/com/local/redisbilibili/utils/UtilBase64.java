package com.local.redisbilibili.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UtilBase64 {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public UtilBase64() { }

    public static String encode(byte[] src) {
        return src.length == 0 ? "" : new String(encodeUseBytes(src), DEFAULT_CHARSET);
    }

    public static byte[] encodeUseBytes(byte[] src) { return src.length == 0 ? src : Base64.getEncoder().encode(src); }

    public static byte[] decode(String str) {
        return str.isEmpty() ? new byte[0] : decodeUseString(str.getBytes(DEFAULT_CHARSET));
    }

    public static byte[] decodeUseString(byte[] src) { return src.length == 0 ? src : Base64.getDecoder().decode(src); }
}
