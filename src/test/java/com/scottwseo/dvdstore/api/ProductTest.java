package com.scottwseo.dvdstore.api;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by seos on 10/19/16.
 */
public class ProductTest {

    private Product newProduct() {
        Product product = new Product();
        product.actor("Scott");
        product.category(1L);
        product.title("Scott Goes to Hollywood");
        product.commonProdId(1L);
        product.price(new BigDecimal(10.00));

        return product;
    }

    @Test
    public void testToString() {
        Product product = newProduct();
        assertThat(product.toString(), is(notNullValue()));
    }

    @Test
    public void testEquals() {
        Product productA = newProduct();

        Product productB = newProduct();

        assertThat(productA.equals(productB), is(true));
    }

    @Test
    public void testToHash() {
        Product productA = newProduct();

        Product productB = newProduct();

        assertThat(productA.hashCode() == productB.hashCode(), is(true));
    }

}
