package com.scottwseo.commons.app;

import com.scottwseo.commons.health.DummyHealthCheck;
import com.scottwseo.commons.resources.StartupCheckListResource;
import com.scottwseo.commons.util.Configs;
import com.scottwseo.commons.util.EnvVariables;
import com.scottwseo.commons.util.PostgreSQLDatabase;
import io.dropwizard.setup.Environment;

import static com.scottwseo.commons.util.ConfigUtil.isRequired;
import static com.scottwseo.commons.util.LogUtil.warn;

/**
 * Created by seos on 11/6/16.
 */
public class LaunchFailureHandler implements LaunchHandler {

    public void run(Environment environment) {
        environment.jersey().register(new StartupCheckListResource());

        environment.healthChecks().register("dummy", new DummyHealthCheck());

        StringBuilder s = new StringBuilder();
        if (!EnvVariables.check()) {
            for (EnvVariables env : EnvVariables.values()) {
                if (isRequired(env) && !env.isProvided()) {
                    s.append("env [" + env.key() + "] missing ");
                }
            }
        }

        if (!Configs.check()) {
            for (Configs config : Configs.values()) {
                if (isRequired(config) && !config.isProvided()) {
                    s.append("config [" + config.key() + "] missing ");
                }
            }
        }
        else if (!PostgreSQLDatabase.check()) {
            s.append("PostgreSQL database connection failure.");
            s.append("url:" + PostgreSQLDatabase.url());
        }

        warn("prelaunch.check.failed", s.toString());

    }

}
