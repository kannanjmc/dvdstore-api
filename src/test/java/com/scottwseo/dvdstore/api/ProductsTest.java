package com.scottwseo.dvdstore.api;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by seos on 10/19/16.
 */
public class ProductsTest {

    private Products newProducts() {
        Products products = new Products();

        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.actor("Scott");
        product.category(1L);
        product.title("Scott Goes to Hollywood");
        product.commonProdId(1L);
        product.price(new BigDecimal(10.00));
        productList.add(product);

        products.setItems(productList);

        return products;
    }

    @Test
    public void testToString() {
        Products products = newProducts();
        assertThat(products.toString(), is(notNullValue()));
    }

    @Test
    public void testEquals() {
        Products productsA = newProducts();

        Products productsB = newProducts();

        assertThat(productsA.equals(productsB), is(true));
    }

    @Test
    public void testToHash() {
        Products productsA = newProducts();

        Products productsB = newProducts();

        assertThat(productsA.hashCode() == productsB.hashCode(), is(true));
    }

}
