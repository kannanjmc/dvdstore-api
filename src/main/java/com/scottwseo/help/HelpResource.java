package com.scottwseo.help;

import com.google.inject.Inject;
import com.scottwseo.auth.User;
import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/help")
@Produces(MediaType.TEXT_HTML)
public class HelpResource {

    private HelpView helpView;

    @Inject
    public HelpResource(HelpView helpView) {
        this.helpView = helpView;
    }

    @GET
    public HelpView getHelpView(@Auth User user) {
        return helpView;
    }

}