package com.local.redisbilibili.utils;


import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class UtilObject {

    private static final String EMPTY_STRING = "";
    private static final String NULL_STRING = "null";
    private static final String ARRAY_START = "{";
    private static final String ARRAY_END = "}";
    private static final String EMPTY_ARRAY = "{}";
    private static final String ARRAY_ELEMENT_SEPARATOR = ",";

    public UtilObject() {}

    public static boolean isEmpty( Object obj) {
        if(obj == null) {
            return true;
        }else if(obj instanceof Optional) {
            return !((Optional)obj).isPresent();
        }else if(obj instanceof CharSequence) {
            return ((CharSequence)obj).length() == 0;
        }else if(obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }else if(obj instanceof Collection) {
            return ((Collection)obj).isEmpty();
        }else{
            return obj instanceof Map ? ((Map)obj).isEmpty() : false;
        }
    }

    public static boolean isNotEmpty(Object obj) { return !isEmpty(obj); }
}
