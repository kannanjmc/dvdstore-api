package com.scottwseo.commons.config;

/**
 * Created by seos on 9/12/16.
 */
public interface Config {

    String name();

    String key();

    String value();

    boolean required();

    boolean masked();

}
