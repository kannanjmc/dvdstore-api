package com.scottwseo.commons.help;

import io.dropwizard.views.View;
import org.pegdown.PegDownProcessor;

import java.io.IOException;

public class OverviewView extends View {

    private String applicationContextPath;

    public OverviewView() {
        super("overview.ftl");
    }

    public String getHtml() throws IOException {

        // Hard coded but could come from anywhere
        String markdown =
                "\n" +
                "# DVD Store API: API Design Demo\n" +
                "\n" +
                "[![license](https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000)](https://github.com/scott-seo/dvdstore-api/blob/master/LICENSE)\n" +
                "[![Build Status](https://travis-ci.org/scott-seo/dvdstore-api.svg?branch=master)](https://travis-ci.org/scott-seo/dvdstore-api)\n" +
                "\n" +
                "## DevOps Discipline\n" +
                "\n" +
                "#### Configuration\n" +
                "  * Credentials secured using S3 and EC2 profile\n" +
                "  * Dynamic config reloading\n" +
                "  * Pre-launch checks for environmental variables and configs \n" +
                "  * Dockerized [image](https://hub.docker.com/r/scottseo/dvdstore-api/)\n" +
                "\n" +
                "#### Logging\n" +
                "  * Log forwarding using fleuntd [image](https://hub.docker.com/r/scottseo/custom-fluentd/)\n" +
                "  * Jsonized\n" +
                "  * Websocket for quick access\n" +
                "\n" +
                "#### Metrics\n" +
                "  * Grafana backed by Graphite\n" +
                "\n" +
                "#### Traceability\n" +
                "  * Use of Correlation ID in logging\n" +
                "\n" +
                "---\n" +
                "\n" +
                "## Development Discipline\n" +
                "\n" +
                "### Continuous Deployment\n" +
                "  * Dark release using feature flags\n" +
                "  <img src=\"https://github.com/scott-seo/dvdstore-api/blob/master/images/togglz-main.png\">\n" +
                "  <img src=\"https://github.com/scott-seo/dvdstore-api/blob/master/images/togglz-activation.png\">\n" +
                "\n" +
                "### Development\n" +
                "  * API specification defined using swagger\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n";

        // New processor each time due to pegdown not being thread-safe internally
        PegDownProcessor processor = new PegDownProcessor();

        // Return the rendered HTML
        return processor.markdownToHtml(markdown);

    }
}
