package com.scottwseo.commons.config;

import java.lang.annotation.Annotation;

/**
 * Created by sseo on 9/6/16.
 */
public class ConfigUtil {

    private static <A extends Annotation> A getAnnotation(Config config, Class<A> annotationType) {
        try {
            Class<? extends Config> configClass = config.getClass();
            A fieldAnnotation = configClass.getField(config.name()).getAnnotation(annotationType);
            A classAnnotation = configClass.getAnnotation(annotationType);

            return fieldAnnotation != null ? fieldAnnotation : classAnnotation;
        } catch (SecurityException e) {
            // ignore
        } catch (NoSuchFieldException e) {
            // ignore
        }
        return null;
    }

    public static boolean isRequired(Config config) {
        Configuration configuration = getAnnotation(config, Configuration.class);
        if (configuration != null) {
            return configuration.required();
        }
        return true;
    }

    public static boolean isMasked(Config config) {
        Configuration configuration = getAnnotation(config, Configuration.class);
        if (configuration != null) {
            return configuration.masked();
        }
        return true;
    }

}
