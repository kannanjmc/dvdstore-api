package com.scottwseo.dvdstore.service;

import com.scottwseo.dvdstore.api.Product;
import com.scottwseo.dvdstore.api.ProductCreate;
import com.scottwseo.dvdstore.api.Products;

import javax.ws.rs.core.SecurityContext;
import java.math.BigDecimal;

public class ProductsServiceImpl implements ProductsService {

    @Override
    public ProductCreate addProduct(ProductCreate body, SecurityContext securityContext) {

        ProductCreate product = new ProductCreate()
                .actor("Scott")
                .category(1L)
                .title("Go")
                .price(new BigDecimal(10.00d))
                .commonProdId(2L);

        return product;
    }
    @Override
    public Products listProducts(Long start, Long size, SecurityContext securityContext) {
        return null;
    }
    @Override
    public ProductCreate updateProduct(ProductCreate body, SecurityContext securityContext) {
        return null;
    }

    @Override
    public boolean deleteProduct(Long productId, String apiKey, SecurityContext securityContext) {
        return false;
    }
    @Override
    public Product getProductById(Long productId, SecurityContext securityContext) {
        return null;
    }

}
