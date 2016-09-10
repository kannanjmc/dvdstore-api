package com.scottwseo.commons.health;

import com.codahale.metrics.health.HealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyHealthCheck extends HealthCheck {

    private static final Logger LOG = LoggerFactory.getLogger(DummyHealthCheck.class);

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }

}
