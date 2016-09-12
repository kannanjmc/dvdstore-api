package com.scottwseo.commons.resources;

import com.scottwseo.commons.util.Config;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
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

    @GET
    public Map<String, Object> index() {
        Map<String, Object> message = new HashMap<>();

        message.put("code", "500");
        message.put("message", "missing configuration");

        List configs = new ArrayList();

        if (!Config.check()) {
            for (Config config : Config.values()) {
                if (!config.isProvided()) {
                    configs.add(config.key());
                }
            }
        }

        message.put("configs", configs);

        return message;

    }

}