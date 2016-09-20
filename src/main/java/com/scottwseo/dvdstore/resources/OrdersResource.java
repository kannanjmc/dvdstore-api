package com.scottwseo.dvdstore.resources;

import com.google.inject.Inject;
import com.scottwseo.dvdstore.api.Order;
import com.scottwseo.dvdstore.service.OrdersService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Map;

/**
 * Created by sseo on 9/6/16.
 */
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdersResource {

    private OrdersService ordersService;

    @Inject
    public OrdersResource(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Response addOrder(Order orderToAdd,
                               @Context SecurityContext securityContext) {

        Order order = ordersService.addOrder(orderToAdd, securityContext);

        if (order.error() != null) {
            return Response.status(400).entity(order.error()).build();
        }

        return Response.ok().entity(order).build();
    }


    @DELETE
    @Path("/{orderId}")
    @Produces({"application/json"})
    public Response deleteProduct(@PathParam("orderId") Long orderId,
                                  @HeaderParam("api_key") String apiKey,
                                  @Context SecurityContext securityContext) {

        Map error = ordersService.deleteOrder(orderId, apiKey, securityContext);

        if (error != null) {
            int statusCode = (int) error.get("statusCode");
            return Response.status(statusCode).entity(error).build();
        }

        return Response.status(204).build();

    }

    @GET
    @Path("/{orderId}")
    @Produces({"application/json"})
    public Response getProductById(@PathParam("orderId") Long orderId,
                                   @Context SecurityContext securityContext) {

        Order order = ordersService.getOrderById(orderId, securityContext);

        if (order.error() != null) {
            int statusCode = (int) order.error().get("statusCode");
            return Response.status(statusCode).entity(order.error()).build();
        }

        return Response.ok().entity(order).build();
    }

}