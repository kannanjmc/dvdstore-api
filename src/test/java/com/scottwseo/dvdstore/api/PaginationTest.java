package com.scottwseo.dvdstore.api;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by seos on 10/19/16.
 */
public class PaginationTest {

    private Pagination newPagination() {
        Pagination pagination = new Pagination();

        pagination.setNext("next");
        pagination.setPrev("prev");
        pagination.setResultSize(10L);
        pagination.setStart(1L);
        pagination.setTotal(100L);
        pagination.setTotalPages(10L);

        return pagination;
    }

    @Test
    public void testToString() {
        Pagination pagination = newPagination();
        assertThat(pagination.toString(), is(notNullValue()));
    }

    @Test
    public void testEquals() {
        Pagination paginationA = newPagination();

        Pagination paginationB = newPagination();

        assertThat(paginationA.equals(paginationA), is(true));

        assertThat(paginationA.equals(null), is(false));

        assertThat(paginationA.equals(paginationB), is(true));
    }

    @Test
    public void testToHash() {
        Pagination paginationA = newPagination();

        Pagination paginationB = newPagination();

        assertThat(paginationA.hashCode() == paginationB.hashCode(), is(true));
    }

}
