package com.scottwseo.dvdstore.service;

import io.swagger.api.*;
import io.swagger.model.*;
import io.swagger.model.ProductCreate;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-09-15T13:14:52.220Z")
public abstract class ProductsApiService {
    public abstract Response addProduct(ProductCreate body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response listProducts(Long start,Long size,SecurityContext securityContext) throws NotFoundException;
    public abstract Response updateProduct(ProductCreate body,SecurityContext securityContext) throws NotFoundException;
}
