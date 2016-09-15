package com.scottwseo.dvdstore.service;

import io.swagger.annotations.ApiParam;
import io.swagger.api.factories.ProductApiServiceFactory;
import io.swagger.jaxrs.*;
import io.swagger.model.*;
import io.swagger.model.ProductCreate;

import javax.ws.rs.*;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/product")


@io.swagger.annotations.Api(description = "the product API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-09-15T13:14:52.220Z")
public class ProductApi  {
   private final ProductApiService delegate = ProductApiServiceFactory.getProductApi();

    @DELETE
    @Path("/{productId}")
    
    @Produces({ "application/xml", "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Deletes a product", notes = "", response = void.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "dvdstore_auth", scopes = {
            @io.swagger.annotations.AuthorizationScope(scope = "write:products", description = "modify products in your account"),
            @io.swagger.annotations.AuthorizationScope(scope = "read:products", description = "read your products")
        })
    }, tags={ "products", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Product not found", response = void.class) })
    public Response deleteProduct(@ApiParam(value = "Product id to delete",required=true) @PathParam("productId") Long productId
,@ApiParam(value = "" )@HeaderParam("api_key") String apiKey
,@Context SecurityContext securityContext)
    throws javax.ws.rs.NotFoundException {
        return delegate.deleteProduct(productId,apiKey,securityContext);
    }
    @GET
    @Path("/{productId}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Find product by ID", notes = "Returns a single product", response = ProductCreate.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "api_key")
    }, tags={ "products", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = ProductCreate.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid ID supplied", response = ProductCreate.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Product not found", response = ProductCreate.class) })
    public Response getProductById(@ApiParam(value = "ID of product to return",required=true) @PathParam("productId") Long productId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getProductById(productId,securityContext);
    }
}
