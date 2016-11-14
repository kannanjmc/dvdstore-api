package com.scottwseo.dvdstore.guice;

import com.scottwseo.dvdstore.config.Configs;
import com.scottwseo.dvdstore.config.EnvVariables;
import com.scottwseo.dvdstore.config.PostgreSQLDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seos on 11/13/16.
 */
public class ErrorMessageBuilder {

    public static List<String> errorMessage() {
        List<String> s = new ArrayList<>();
        if (!EnvVariables.check()) {
            for (EnvVariables env : EnvVariables.values()) {
                if (env.required() && !env.provided()) {
                    s.add("env [" + env.key() + "] missing ");
                }
            }
        }

        if (!Configs.check()) {
            for (Configs config : Configs.values()) {
                if (config.required() && !config.required()) {
                    s.add("config [" + config.key() + "] missing ");
                }
            }
        }
        else if (!PostgreSQLDatabase.check()) {
            s.add("PostgreSQL database connection failure.");
            s.add("url:" + PostgreSQLDatabase.url());
        }

        return s;
    }

}
