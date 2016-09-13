package com.scottwseo.commons.cfg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sseo on 9/6/16.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Configuration {

    ConfigDataType type();

    String defaultValue() default "";

    String example() default "";

    boolean required() default true;

    boolean masked() default false;

}
