package com.scottwseo.dvdstore.jdbi;

import com.scottwseo.dvdstore.api.Product;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static com.scottwseo.commons.util.LogUtil.warn;

public class ProductMapper implements ResultSetMapper<Product> {

    public Product map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        Product product = new Product();

        try {
            product.actor(r.getString("actor"))
                    .category(r.getLong("category"))
                    .commonProdId(r.getLong("common_prod_id"))
                    .price(r.getBigDecimal("price"))
                    .special(r.getBoolean("special"))
                    .prodId(r.getLong("prod_id"));
        }
        catch (Exception e) {
            Map error = warn("rset to product mapping failed", e.getMessage(), "ctx", ctx);
            product.error(error);
        }

        return product;
    }

}