package com.scottwseo.commons.util;

/**
 * Created by seos on 9/10/16.
 */
public class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

}
