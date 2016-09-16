package com.scottwseo.dvdstore.service;

import com.scottwseo.dvdstore.api.Product;
import com.scottwseo.dvdstore.api.ProductCreate;
import com.scottwseo.dvdstore.api.Products;

import javax.ws.rs.core.SecurityContext;

public interface ProductsService {

    ProductCreate addProduct(ProductCreate post, SecurityContext securityContext);

    Products listProducts(Long start, Long size, SecurityContext securityContext, String baseUri);

    ProductCreate updateProduct(ProductCreate post, SecurityContext securityContext);

    boolean deleteProduct(Long productId, String apiKey, SecurityContext securityContext);

    Product getProductById(Long productId, SecurityContext securityContext);

}
