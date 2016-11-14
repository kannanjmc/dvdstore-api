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
        String markdown = "";

        // New processor each time due to pegdown not being thread-safe internally
        PegDownProcessor processor = new PegDownProcessor();

        // Return the rendered HTML
        return processor.markdownToHtml(markdown);

    }
}
