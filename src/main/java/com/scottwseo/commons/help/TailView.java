package com.scottwseo.commons.help;

import io.dropwizard.views.View;

/**
 * Created by seos on 4/14/15.
 */
public class TailView extends View {

    private String applicationContextPath;

    public TailView(String applicationContextPath) {
        super("tail.ftl");
        this.applicationContextPath = applicationContextPath;
    }

    public String getBase() {
        return applicationContextPath;
    }

}
