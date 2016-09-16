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

        String sql = null;
        try (Handle h = dbi.open()) {
            sql = "INSERT\n" +
                    "INTO\n" +
                    "    public.products\n" +
                    "    (\n" +
                    "        category,\n" +
                    "        title,\n" +
                    "        actor,\n" +
                    "        price,\n" +
                    "        special,\n" +
                    "        common_prod_id,\n" +
                    "        special\n" +
                    "    )\n" +
                    "    VALUES\n" +
                    "    (\n" +
                    "        :category,\n" +
                    "        :title,\n" +
                    "        :actor,\n" +
                    "        :price,\n" +
                    "        :special,\n" +
                    "        :common_prod_id,\n" +
                    "        :special\n" +
                    "    )";
            long prodId = h.createStatement(sql.toString())
            .bind("category", product.getCategory())
            .bind("title", product.getTitle())
            .bind("actor", product.getActor())
            .bind("price", product.getPrice())
            .bind("special", 1)
            .bind("common_prod_id", product.getCommonProdId())
            .bind("special", product.isSpecial())
            .executeAndReturnGeneratedKeys(LongColumnMapper.PRIMITIVE).first();

            return new ProductCreate()
                    .prodId(prodId)
                    .actor(product.getActor())
                    .category(product.getCategory())
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .commonProdId(product.getCommonProdId());

            // return new ProductCreate().error(map("products.addproduct.failed", "Please check your input", "product", product));
        }
        catch (Exception e) {
            Map error = warn("products.addproduct.failed", "Please check your input", "product", product);
            return new ProductCreate().error(error);
        }

    }

    @Override
    public Products listProducts(Long start, Long size, SecurityContext securityContext, String baseUri) {

        try (Handle h = dbi.open()) {

            List<Product> products = new ArrayList<>();

            long offset = (start == 1 || start == 0) ? 0 : (size * size) - size;

            String sql = "select * from public.products offset :offset limit :limit";

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
