package com.scottwseo.dvdstore.resources;

import com.google.inject.Inject;
import com.scottwseo.dvdstore.api.Customer;
import com.scottwseo.dvdstore.service.CustomersService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by sseo on 9/6/16.
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomersResource {

    private CustomersService customersService;

    @Inject
    public CustomersResource(CustomersService customersService) {
        this.customersService = customersService;
    }

    @GET
    @Path("/{customerId}")
    @Produces({"application/json"})
    public Response getCustomerById(@PathParam("customerId") Long customerId,
                                   @Context SecurityContext securityContext) {

        Customer customer = customersService.getCustomerById(customerId, securityContext);

        if (customer.error() != null) {
            int statusCode = (int) customer.error().get("statusCode");
            return Response.status(statusCode).entity(customer.error()).build();
        }

        return Response.ok().entity(customer).build();
    }

}