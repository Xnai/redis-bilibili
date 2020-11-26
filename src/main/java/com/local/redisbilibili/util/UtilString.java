package com.local.redisbilibili.util;

import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.UUID;

public class UtilString {
    public static final String EMPTY = "";
    public static final String COMMA_EN = ",";
    public static final String DASH = "-";
    public static final String UNDERLINE = "_";
    public static final String DOT = ".";
    public static final String IMAGE_BASE64_PREFIX = "data:image/jpeg;base64,";
    public static final String STAR = "*";


    public UtilString() {}

    public static boolean isEmpty(CharSequence str){
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence str) { return !isEmpty(str); }

    public static String str(CharSequence cs) {return null == cs ? null : cs.toString(); }

    public static String str(byte[] bytes) { return new String(bytes, StandardCharsets.UTF_8); }

    public static String str(Blob blob) {
        if(!UtilObject.isEmpty(blob)) {
            try{
                return str(blob.getBytes(1L, Integer.parseInt(blob.length() + "")));
            }catch(SQLException ex) {
                System.out.println("读取blob出错：" + ex.getMessage());
            }
        }
        return "";
    }

    public static String strBase64(Blob blob) {
        if(!UtilObject.isEmpty(blob)) {
            try {
                return UtilBase64.encode(blob.getBytes(1L, Integer.parseInt(blob.length() + "")));
            }catch(SQLException ex) {
                System.out.println("读取blob出错：" + ex.getMessage());
            }
        }
        return "";
    }

    public static String subSuf(CharSequence str, int formIndex) {
        return isEmpty(str) ? str(str) : sub(str, formIndex, str.length());
    }

    public static String sub(CharSequence str, int fromIndex, int toIndex) {
        if(isEmpty(str)) {
            return str(str);
        }else {
            int len = str.length();
            if(fromIndex < 0) {
                fromIndex += len;
                if(fromIndex < 0) {
                    fromIndex = 0;
                }
            }else if(fromIndex > len) {
                fromIndex = len;
            }
            if(toIndex < 0) {
                toIndex += len;
                if(toIndex < 0) {
                    toIndex = len;
                }
            }else if(toIndex > len) {
                toIndex = len;
            }
            if(toIndex < fromIndex) {
                int tmp = fromIndex;
                fromIndex = toIndex;
                toIndex = tmp;
            }
            return fromIndex == toIndex ? "" : str.toString().substring(fromIndex, toIndex);
        }
    }

    /**
     * 模糊化字符串
     *
     * @param str        待处理字符串
     * @param maskChar   用它进行模糊化
     * @param startIndex 开始位置
     * @param endIndex   结束位置
     * @return 处理后字符串
     */
    public static String maskStr(String str, String maskChar, int startIndex, int endIndex) {
        if (UtilString.isEmpty(str)) {
            return UtilString.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (i >= startIndex && i < endIndex) {
                sb.append(maskChar);
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    public static String lowerUUID32() { return uuid().replaceAll(DASH,"").toLowerCase(); }

    public static String uuid() {return UUID.randomUUID().toString(); }

    public static String addImageBase64Prefix(String base64) { return IMAGE_BASE64_PREFIX + base64; }

    public static String removeImageBase64Prefix(String base64WithPerfix) { return subSuf(base64WithPerfix, IMAGE_BASE64_PREFIX.length()); }
}