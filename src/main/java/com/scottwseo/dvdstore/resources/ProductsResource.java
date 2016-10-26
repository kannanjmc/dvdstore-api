package com.scottwseo.dvdstore.resources;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.ClientTracer;
import com.google.inject.Inject;
import com.scottwseo.dvdstore.api.Product;
import com.scottwseo.dvdstore.api.ProductCreate;
import com.scottwseo.dvdstore.api.Products;
import com.scottwseo.dvdstore.service.ProductsService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.*;
import java.util.Map;

import static com.scottwseo.commons.util.LogUtil.info;

/**
 * Created by sseo on 9/6/16.
 */
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductsResource {

    private ProductsService productsService;

    private Client client;

    private Brave brave;

    @Inject
    public ProductsResource(ProductsService productsService, Client client, Brave brave) {
        this.productsService = productsService;
        this.client = client;
        this.brave = brave;
    }

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Response addProduct(ProductCreate post,
                               @Context HttpServletRequest request,
                               @Context SecurityContext securityContext) throws Exception {

        ClientTracer clientTracer = brave.clientTracer();
        clientTracer.startNewSpan("database");
        clientTracer.setClientSent();
        ProductCreate product = productsService.addProduct(post, securityContext);
        clientTracer.setClientReceived();

        if (product.error() != null) {
            return Response.status(400).entity(product.error()).build();
        }

        info("product.create.successful", "", "product", product);

        return client.target("http://127.0.0.1:" + request.getServerPort() + "/api/v1/dvdstore/products/" + product.getProdId()).request().get();

    }

    @GET
    @Produces({ "application/json" })
    public Response listProducts(@QueryParam("start") Long start,
                                 @QueryParam("size") Long size,
                                 @Context SecurityContext securityContext,
                                 @Context UriInfo uriInfo) {

        Products products = productsService.listProducts(start, size, securityContext, uriInfo.getBaseUri().toString());

        if (products.error() != null) {
            return Response.status(400).entity(products.error()).build();
        }

        return Response.ok().entity(products).build();
    }

    @PUT
    @Consumes({ "application/json"})
    @Produces({ "application/json" })
    public Response updateProduct(ProductCreate product,
                                  @Context SecurityContext securityContext) {

        ProductCreate productUpdated = productsService.updateProduct(product,securityContext);

        if (productUpdated.error() != null) {
            int statusCode = (int) productUpdated.error().get("statusCode");
            return Response.status(statusCode).entity(productUpdated
                    .error()).build();
        }

        info("product.update.successful", "", "product", productUpdated);

        return Response.status(204).build();
    }

    @DELETE
    @Path("/{productId}")
    @Produces({"application/json"})
    public Response deleteProduct(@PathParam("productId") Long productId,
                                  @HeaderParam("api_key") String apiKey,
                                  @Context SecurityContext securityContext) {
        Map error = productsService.deleteProduct(productId, apiKey, securityContext);

        if (error != null) {
            int statusCode = (int) error.get("statusCode");
            return Response.status(statusCode).entity(error).build();
        }

        return Response.status(204).build();

    }

    @GET
    @Path("/{productId}")
    @Produces({"application/json"})
    public Response getProductById(@PathParam("productId") Long productId,
                                   @Context SecurityContext securityContext) {

        Product product = productsService.getProductById(productId, securityContext);

        if (product.error() != null) {
            int statusCode = (int) product.error().get("statusCode");
            return Response.status(statusCode).entity(product.error()).build();
        }

        return Response.ok().entity(product).build();
    }

}