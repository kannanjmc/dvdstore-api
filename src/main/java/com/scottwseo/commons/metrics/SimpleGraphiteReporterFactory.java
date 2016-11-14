package com.scottwseo.commons.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.metrics.graphite.GraphiteReporterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public abstract class SimpleGraphiteReporterFactory extends GraphiteReporterFactory {

    private static Logger LOG = LoggerFactory.getLogger(SimpleGraphiteReporterFactory.class);

    private String appname;

    @JsonProperty
    public String getAppname() {
        return appname;
    }

    @JsonProperty
    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPrefix() {
        return getAppname() + "." + getHostname();
    }

    @Override
    public ScheduledReporter build(MetricRegistry registry) {
        String host = getGraphiteHostname();

        if (host == null) {
            LOG.info("graphite host name missing");
            return new DummyScheduledReporter(registry);
        }

        return GraphiteReporter.forRegistry(registry)
                .convertDurationsTo(getDurationUnit())
                .convertRatesTo(getRateUnit())
                .filter(getFilter())
                .prefixedWith(getPrefix())
                .build(new Graphite(new InetSocketAddress(host, getPort())));
    }

    private String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException uhe) {
            return "unknown";
        }
    }

    protected abstract String getGraphiteHostname();
}
