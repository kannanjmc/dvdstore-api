package com.scottwseo.commons.help;

import io.dropwizard.views.View;

/**
 * Created by seos on 4/14/15.
 */
public class HelpView extends View {

    public HelpView() {
        super("help.ftl");
    }

    public String getBase() {
        return "/api/v1/commons";
    }

}
