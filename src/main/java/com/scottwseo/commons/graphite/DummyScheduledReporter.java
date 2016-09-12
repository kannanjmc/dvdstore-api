package com.scottwseo.commons.graphite;

import com.codahale.metrics.*;

import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by seos on 9/10/16.
 */
public class DummyScheduledReporter extends ScheduledReporter {

    public DummyScheduledReporter(MetricRegistry registry) {
        super(registry, "dummy-reporter", MetricFilter.ALL, TimeUnit.MINUTES, TimeUnit.SECONDS);
    }

    @Override
    public void report(SortedMap<String, Gauge> gauges, SortedMap<String, Counter> counters, SortedMap<String, Histogram> histograms, SortedMap<String, Meter> meters, SortedMap<String, Timer> timers) {
        // dummy reporter, so do nothing
    }

}
