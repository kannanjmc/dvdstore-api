package com.scottwseo.dvdstore.resources;

import com.google.inject.Inject;
import com.scottwseo.dvdstore.api.Customer;
import com.scottwseo.dvdstore.service.CustomersService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Map;

/**
 * Created by sseo on 9/6/16.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomersResource {

    private CustomersService customersService;

    @Inject
    public CustomersResource(CustomersService customersService) {
        this.customersService = customersService;
    }

    @POST
    @Produces({"application/json"})
    public Response createCustomer(Customer customer,
                                   @Context SecurityContext securityContext) {
        Customer customerCreated = customersService.createCustomer(customer, securityContext);

        if (customerCreated.error() != null) {
            int statusCode = (int) customer.error().get("statusCode");
            return Response.status(statusCode).entity(customer.error()).build();
        }

        return Response.ok().entity(customerCreated).build();
    }

    @GET
    @Path("/{username}")
    @Produces({"application/json"})
    public Response getCustomerByUsername(@PathParam("username") String username,
                                          @Context SecurityContext securityContext) {

        Customer customer = customersService.getCustomerByUsername(username, securityContext);

        if (customer.error() != null) {
            int statusCode = (int) customer.error().get("statusCode");
            return Response.status(statusCode).entity(customer.error()).build();
        }

        return Response.ok().entity(customer).build();
    }

    @DELETE
    @Path("/{username}")
    @Produces({"application/json"})
    public Response deleteCustomerByUsername(@PathParam("username") String username,
                                             @Context SecurityContext securityContext) {

        Map error = customersService.deleteCustomerByUsername(username, securityContext);

        if (error != null) {
            int statusCode = (int) error.get("statusCode");
            return Response.status(statusCode).entity(error).build();
        }

        return Response.status(204).build();
    }

    @PUT
    @Path("/{username}")
    @Produces({"application/json"})
    public Response updateCustomerByUsername(Customer customer,
                                             @PathParam("username") String username,
                                             @Context SecurityContext securityContext) {

        Customer updatedCustomer = customersService.updateCustomerByUsername(customer, username, securityContext);

        if (updatedCustomer.error() != null) {
            int statusCode = (int) updatedCustomer.error().get("statusCode");
            return Response.status(statusCode).entity(updatedCustomer.error()).build();
        }

        return Response.status(204).build();
    }

    @GET
    @Path("/login")
    public Response loginCustomer(@QueryParam("username") String username,
                                  @QueryParam("password") String password,
                                  @Context SecurityContext securityContext) {

        Customer customer = customersService.getCustomerByUsername(username, securityContext);

        if (password != null && password.equals(customer.getPassword())) {
            return Response.status(200).build();
        }
        else {
            return Response.status(400).build();
        }
    }

    @GET
    @Path("/logout")
    @Produces({"application/json"})
    public Response logoutCustomer(@QueryParam("username") String username,
                                   @Context SecurityContext securityContext) {
        return Response.status(200).build();
    }

}