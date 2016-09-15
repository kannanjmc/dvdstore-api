package com.scottwseo.dvdstore.service;

import io.swagger.annotations.ApiParam;
import io.swagger.api.factories.ProductsApiServiceFactory;
import io.swagger.jaxrs.*;
import io.swagger.model.*;
import io.swagger.model.ProductCreate;
import io.swagger.model.Products;

import javax.ws.rs.*;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/products")


@io.swagger.annotations.Api(description = "the products API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-09-15T13:14:52.220Z")
public class ProductsApi  {
   private final ProductsApiService delegate = ProductsApiServiceFactory.getProductsApi();

    @POST
    
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/xml", "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Add a new product to the store", notes = "", response = void.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "dvdstore_auth", scopes = {
            @io.swagger.annotations.AuthorizationScope(scope = "write:products", description = "modify products in your account"),
            @io.swagger.annotations.AuthorizationScope(scope = "read:products", description = "read your products")
        })
    }, tags={ "products", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 405, message = "Invalid input", response = void.class) })
    public Response addProduct(@ApiParam(value = "Product object that needs to be added to the store" ,required=true) ProductCreate body
,@Context SecurityContext securityContext)
    throws javax.ws.rs.NotFoundException {
        return delegate.addProduct(body,securityContext);
    }
    @GET
    
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Lists products", notes = "Returns a list of products", response = Products.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "api_key")
    }, tags={ "products", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Products.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Product not found", response = Products.class) })
    public Response listProducts(@ApiParam(value = "Starting index of page",required=true) @QueryParam("start") Long start
,@ApiParam(value = "Size of array",required=true) @QueryParam("size") Long size
,@Context SecurityContext securityContext)
    throws javax.ws.rs.NotFoundException {
        return delegate.listProducts(start,size,securityContext);
    }
    @PUT
    
    @Consumes({ "application/json", "application/xml" })
    @Produces({ "application/xml", "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Update an existing product", notes = "", response = void.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "dvdstore_auth", scopes = {
            @io.swagger.annotations.AuthorizationScope(scope = "write:products", description = "modify products in your account"),
            @io.swagger.annotations.AuthorizationScope(scope = "read:products", description = "read your products")
        })
    }, tags={ "products", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Product not found", response = void.class),
        
        @io.swagger.annotations.ApiResponse(code = 405, message = "Validation exception", response = void.class) })
    public Response updateProduct(@ApiParam(value = "Product object that needs to be added to the store" ,required=true) ProductCreate body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.updateProduct(body,securityContext);
    }
}
