package com.scottwseo.commons.util;

import java.util.ArrayList;
import java.util.List;

import static com.scottwseo.commons.util.StringUtils.isEmpty;
import static com.scottwseo.commons.util.StringUtils.isNotEmpty;

/**
 * Created by seos on 9/8/16.
 */
public class EnvVariables {

    private EnvVariables() {
    }

    public static final String S3_BUCKET = "com.scottwseo.api.S3_BUCKET";

    public static final String S3_KEY = "com.scottwseo.api.S3_KEY";

    public static final String ACCESS_KEY_ID = "com.scottwseo.api.ACCESS_KEY_ID";

    public static final String SECRET_KEY = "com.scottwseo.api.SECRET_KEY";

    public static boolean check() {
        return isNotEmpty(System.getenv(S3_BUCKET)) &&
                isNotEmpty(System.getenv(S3_KEY)) &&
                isNotEmpty(System.getenv(ACCESS_KEY_ID)) &&
                isNotEmpty(System.getenv(SECRET_KEY));
    }

    public static List<String> missing() {
        List<String> s = new ArrayList<>();
        if (isEmpty(System.getenv(S3_BUCKET))) {
            s.add("env [" + S3_BUCKET + "] missing");
        }
        if (isEmpty(System.getenv(S3_KEY))) {
            s.add("env [" + S3_KEY + "] missing");
        }
        if (isEmpty(System.getenv(ACCESS_KEY_ID))) {
            s.add("env [" + ACCESS_KEY_ID + "] missing");
        }
        if (isEmpty(System.getenv(SECRET_KEY))) {
            s.add("env [" + SECRET_KEY + "] missing");
        }

        return s;
    }

}
