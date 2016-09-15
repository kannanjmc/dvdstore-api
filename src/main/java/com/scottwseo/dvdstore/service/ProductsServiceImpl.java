package com.scottwseo.dvdstore.service;

import com.google.inject.Inject;
import com.scottwseo.dvdstore.api.Product;
import com.scottwseo.dvdstore.api.ProductCreate;
import com.scottwseo.dvdstore.api.Products;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.LongColumnMapper;

import javax.ws.rs.core.SecurityContext;

public class ProductsServiceImpl implements ProductsService {

    private DBI dbi;

    @Inject
    public ProductsServiceImpl(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public ProductCreate addProduct(ProductCreate product, SecurityContext securityContext) {

        try (Handle h = dbi.open()) {
            String sql = "INSERT\n" +
                    "INTO\n" +
                    "    public.products\n" +
                    "    (\n" +
                    "        category,\n" +
                    "        title,\n" +
                    "        actor,\n" +
                    "        price,\n" +
                    "        special,\n" +
                    "        common_prod_id\n" +
                    "    )\n" +
                    "    VALUES\n" +
                    "    (\n" +
                    "        :category,\n" +
                    "        :title,\n" +
                    "        :actor,\n" +
                    "        :price,\n" +
                    "        :special,\n" +
                    "        :common_prod_id\n" +
                    "    )";
            long prodId = h.createStatement(sql.toString())
            .bind("category", product.getCategory())
            .bind("title", product.getTitle())
            .bind("actor", product.getActor())
            .bind("price", product.getPrice())
            .bind("special", 1)
            .bind("common_prod_id", product.getCommonProdId())
            .executeAndReturnGeneratedKeys(LongColumnMapper.PRIMITIVE).first();

            return new ProductCreate()
                    .prodId(prodId)
                    .actor(product.getActor())
                    .category(product.getCategory())
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .commonProdId(product.getCommonProdId());
        }
        catch (Exception e) {
            return null;
        }
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
