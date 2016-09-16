package com.scottwseo.commons.health;

import com.codahale.metrics.health.HealthCheck;
import com.scottwseo.commons.util.Configs;

import static com.scottwseo.commons.util.ConfigUtil.isRequired;

public class ConfigHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {

        boolean valid = Configs.check();

        if (valid) {
            return Result.healthy();
        } else {
            StringBuilder s = new StringBuilder();

            for (Configs config : Configs.values()) {
                if (isRequired(config) && !config.isProvided()) {
                    s.append("config [" + config.key() + "] missing");
                }
            }

            return Result.unhealthy(s.toString());
        }
    }

}
