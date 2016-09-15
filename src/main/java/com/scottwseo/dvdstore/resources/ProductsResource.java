package com.scottwseo.dvdstore.resources;

import com.google.inject.Inject;
import com.scottwseo.dvdstore.api.Product;
import com.scottwseo.dvdstore.api.ProductCreate;
import com.scottwseo.dvdstore.api.Products;
import com.scottwseo.dvdstore.service.ProductsService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by sseo on 9/6/16.
 */
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductsResource {

    private ProductsService productsService;

    @Inject
    public ProductsResource(ProductsService productsService) {
        this.productsService = productsService;
    }

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Response addProduct(ProductCreate post,
                               @Context SecurityContext securityContext) {

        ProductCreate product = productsService.addProduct(post, securityContext);

        return Response.ok().entity(product).build();
    }

    @GET
    @Produces({ "application/json" })
    public Response listProducts(@QueryParam("start") Long start,
                                 @QueryParam("size") Long size,
                                 @Context SecurityContext securityContext) {

        Products products = productsService.listProducts(start,size,securityContext);

        return null;
    }

    @PUT
    @Consumes({ "application/json"})
    @Produces({ "application/json" })
    public Response updateProduct(ProductCreate body,
                                  @Context SecurityContext securityContext) {

        ProductCreate product = productsService.updateProduct(body,securityContext);

        return null;
    }

    @DELETE
    @Path("/{productId}")
    @Produces({"application/json"})
    public Response deleteProduct(@PathParam("productId") Long productId,
                                  @HeaderParam("api_key") String apiKey,
                                  @Context SecurityContext securityContext) {
        boolean successful = productsService.deleteProduct(productId, apiKey, securityContext);

        return null;
    }

    @GET
    @Path("/{productId}")
    @Produces({"application/json"})
    public Response getProductById(@PathParam("productId") Long productId,
                                   @Context SecurityContext securityContext) {

        Product product = productsService.getProductById(productId, securityContext);

        return null;
    }

}