package com.scottwseo.commons.resources;

import com.google.inject.Inject;
import com.scottwseo.commons.auth.User;
import com.scottwseo.commons.help.HelpView;
import io.dropwizard.auth.Auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class HelpResource {

    private HelpView helpView;

    @Inject
    public HelpResource(HelpView helpView) {
        this.helpView = helpView;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/help")
    public HelpView help(@Auth User user) {
        return helpView;
    }

    @GET
    @Path("/meta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Map meta() {

        Map<String, Object> meta = new HashMap();

        meta.put("tag", readTagFromFile());
        meta.put("appname", "some-api");
        return meta;

    }

    private String readTagFromFile() {
        return "v1.0.0";
    }

}