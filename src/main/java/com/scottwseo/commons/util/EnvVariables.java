package com.scottwseo.commons.util;

import static com.scottwseo.commons.util.StringUtils.isNotEmpty;

/**
 * Created by seos on 9/8/16.
 */
public class EnvVariables {

    private EnvVariables() {
    }

    public static final String S3_BUCKET = "com.scottwseo.api.S3_BUCKET";

    public static final String S3_KEY = "com.scottwseo.api.S3_KEY";

    public static final String ACCESS_KEY_ID = "com.scottseo.api.ACCESS_KEY_ID";

    public static final String SECRET_KEY = "com.scottseo.api.SECRET_KEY";

    public static boolean check() {
        return isNotEmpty(System.getenv(S3_BUCKET)) &&
                isNotEmpty(System.getenv(S3_KEY)) &&
                isNotEmpty(System.getenv(ACCESS_KEY_ID)) &&
                isNotEmpty(System.getenv(SECRET_KEY));
    }

}
