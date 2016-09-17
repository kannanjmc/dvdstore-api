package com.scottwseo.dvdstore.service;

import com.google.inject.Inject;
import com.scottwseo.dvdstore.api.Product;
import com.scottwseo.dvdstore.api.ProductCreate;
import com.scottwseo.dvdstore.api.Products;
import com.scottwseo.dvdstore.jdbi.ProductMapper;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerColumnMapper;
import org.skife.jdbi.v2.util.LongColumnMapper;

import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.scottwseo.commons.util.LogUtil.map;
import static com.scottwseo.commons.util.LogUtil.warn;
import static com.scottwseo.dvdstore.util.ResourcesUtil.paginate;

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
                    "    products\n" +
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
            .bind("special", product.isSpecial() ? 1 : 0)
            .bind("common_prod_id", product.getCommonProdId())
            .executeAndReturnGeneratedKeys(LongColumnMapper.PRIMITIVE).first();

            return product.prodId(prodId);
        }
        catch (Exception e) {
            Map error = warn("products.addproduct.failed", e.getMessage(), "product", product);
            return product.error(error);
        }

    }

    @Override
    public Products listProducts(Long start, Long size, SecurityContext securityContext, String baseUri) {

        try (Handle h = dbi.open()) {

            List<Product> products = new ArrayList<>();

            long offset = (start == 1 || start == 0) ? 0 : (start * size) - size;

            String sql = "select * from products order by prod_id offset :offset limit :limit";

            products = h.createQuery(sql)
                .bind("offset", offset)
                .bind("limit", size)
                .map(new ProductMapper())
                .list();

            return new Products().pagination(paginate(baseUri, start, size, totalRecords()))
                    .items(products);
        }
        catch (Exception e) {
            Map error = warn("products.list.failed", e.getMessage(), "start", start, "size", size);
            return new Products().error(error);
        }
    }

    @Override
    public ProductCreate updateProduct(ProductCreate product, SecurityContext securityContext) {

        Product existingProduct = getProductById(product.getProdId(), securityContext);

        if (existingProduct.error() != null) {
            return product.error(existingProduct.error());
        }

        try (Handle h = dbi.open()) {
            String sql =
                    "UPDATE\n" +
                    "    products\n" +
                    "SET\n" +
                    "    category = :category,\n" +
                    "    title = :title,\n" +
                    "    actor = :actor,\n" +
                    "    price = :price,\n" +
                    "    special = :special,\n" +
                    "    common_prod_id = :common_prod_id\n" +
                    "WHERE\n" +
                    "    prod_id = :prod_id";
            long rowsUpdated = h.createStatement(sql.toString())
                    .bind("category", product.getCategory())
                    .bind("title", product.getTitle())
                    .bind("actor", product.getActor())
                    .bind("price", product.getPrice())
                    .bind("special", product.isSpecial() ? 1 : 0)
                    .bind("common_prod_id", product.getCommonProdId())
                    .bind("prod_id", product.getProdId())
                    .execute();

            if (rowsUpdated == 1) {
                return product;
            }
            else {
                Map error = warn("product.update.unexepected", "updated affected multiple row", "product", product, "statusCode", 500);
                return product.error(error);
            }
        }
        catch (Exception e) {
            Map error = warn("product.update.failed", e.getMessage(), "product", product, "statusCode", 500);
            return product.error(error);
        }
    }

    @Override
    public Map deleteProduct(Long productId, String apiKey, SecurityContext securityContext) {
        Product existingProduct = getProductById(productId, securityContext);

        if (existingProduct.error() != null) {
            return existingProduct.error();
        }

        try (Handle h = dbi.open()) {
            String sql =
                    "DELETE\n" +
                            "FROM\n" +
                            "    products\n" +
                            "WHERE\n" +
                            "    prod_id = :prod_id";
            int rows = h.createStatement(sql.toString())
                    .bind("prod_id", productId)
                    .execute();

            if (rows == 1) {
                return null;
            }
            else {
                return map("product.delete.failed", "delete affected more than one row", "prod_id", productId, "statusCode", 500);
            }
        }
        catch (Exception e) {
            Map error = warn("product.delete.failed", e.getMessage(), "prod_id", productId, "statusCode", 500);
            return new Products().error(error);
        }
    }

    @Override
    public Product getProductById(Long productId, SecurityContext securityContext) {
        try (Handle h = dbi.open()) {
            String sql =
                    "SELECT\n" +
                            "    prod_id,\n" +
                            "    category,\n" +
                            "    title,\n" +
                            "    actor,\n" +
                            "    price,\n" +
                            "    special,\n" +
                            "    common_prod_id\n" +
                            "FROM\n" +
                            "    products\n" +
                            "WHERE\n" +
                            "    prod_id = :prod_id";
            Product product = h.createQuery(sql.toString())
                    .bind("prod_id", productId)
                    .map(new ProductMapper())
                    .first();

            if (product == null) {
                return new Product().error(map("product.findbyid.failed", "", "prod_id", productId, "statusCode", 404));
            }

            return product;
        }
        catch (Exception e) {
            Map error = warn("product.update.failed", e.getMessage(), "prod_id", productId, "statusCode", 500);
            return new Products().error(error);
        }
    }

    private Long totalRecords() {
        try (Handle h = dbi.open()) {

            return new Long(h.createQuery("select count(*) from products")
                        .map(IntegerColumnMapper.PRIMITIVE).first());
        }
        catch (Exception e) {
            return null;
        }
    }

}
