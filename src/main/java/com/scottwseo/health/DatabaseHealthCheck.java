package com.scottwseo.health;

import com.codahale.metrics.health.HealthCheck;
import com.scottwseo.util.Config;
import com.scottwseo.util.JDBIUtil;

public class DatabaseHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {

        boolean valid = JDBIUtil.check();

        if (valid) {
            return Result.healthy();
        } else {
            return Result.unhealthy("database connection failed");
        }
    }

}
