package com.scottwseo.dvdstore.resources;

import com.google.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by sseo on 9/6/16.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductsResource {

    private ProductsService productsService;

    @Inject
    public ProductsResource(ProductsService productsService) {
        this.productsService = productsService;
    }

/*    @GET
    @Path("/products")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<String> categories() {
        LoggerFactory.getLogger(CategoryService.class).warn("testing websocket");

        return productsService.getCategories();
    }*/

}