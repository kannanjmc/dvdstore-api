package com.scottwseo.health;

import com.codahale.metrics.health.HealthCheck;
import com.scottwseo.util.Config;

public class ConfigHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {

        boolean valid = Config.check();

        if (valid) {
            return Result.healthy();
        } else {
            StringBuilder s = new StringBuilder();

            for (Config config : Config.values()) {
                if (!config.isProvided()) {
                    s.append("config [" + config.key() + "] missing");
                }
            }

            return Result.unhealthy(s.toString());
        }
    }

}
