package com.scottwseo.commons.rest.resources;

import com.google.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sseo on 9/6/16.
 */
@Path("/{default: .*}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StartupCheckListResource {

    private List<String> errorMessages;

    public StartupCheckListResource(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    @GET
    public Map<String, Object> index() {
        Map<String, Object> message = new HashMap<>();

        message.put("code", "500");
        message.put("message", "check failures");

        message.put("failures", errorMessages);

        return message;

    }

}