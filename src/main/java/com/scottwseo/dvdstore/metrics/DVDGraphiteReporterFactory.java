package com.scottwseo.dvdstore.metrics;

import com.scottwseo.commons.metrics.SimpleGraphiteReporterFactory;
import com.scottwseo.dvdstore.config.Configs;

/**
 * Created by seos on 11/13/16.
 */
public class DVDGraphiteReporterFactory extends SimpleGraphiteReporterFactory {

    @Override
    protected String getGraphiteHostname() {
        return Configs.GRAPHITE_HOST.value();
    }

}
