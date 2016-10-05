package com.scottwseo.commons.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by seos on 10/5/16.
 */
public class StringUtilsTest {

    @Test
    public void isEmpty() throws Exception {
        assertFalse(StringUtils.isEmpty("foo"));

        assertTrue(StringUtils.isEmpty(""));
        assertTrue(StringUtils.isEmpty(null));
    }

    @Test
    public void isNotEmpty() throws Exception {
        assertFalse(StringUtils.isNotEmpty(""));
        assertFalse(StringUtils.isNotEmpty(null));

        assertTrue(StringUtils.isNotEmpty("foo"));
    }

}