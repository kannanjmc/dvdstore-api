package com.scottwseo.dvdstore.api;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by seos on 10/19/16.
 */
public class ProductCreateTest {

    private ProductCreate newProductCreate() {
        ProductCreate product = new ProductCreate();
        product.actor("Scott");
        product.category(1L);
        product.title("Scott Goes to Hollywood");
        product.commonProdId(1L);
        product.price(new BigDecimal(10.00));

        return product;
    }

    @Test
    public void testToString() {
        ProductCreate product = newProductCreate();
        assertThat(product.toString(), is(notNullValue()));
    }

    @Test
    public void testEquals() {
        ProductCreate productA = newProductCreate();

        ProductCreate productB = newProductCreate();

        assertThat(productA.equals(productB), is(true));
    }

    @Test
    public void testToHash() {
        ProductCreate productA = newProductCreate();

        ProductCreate productB = newProductCreate();

        assertThat(productA.hashCode() == productB.hashCode(), is(true));
    }

}
