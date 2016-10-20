package com.scottwseo.commons.util;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by seos on 10/19/16.
 */
public class LogUtilTest {

    @Test
    public void testDebug() {
        Map map = LogUtil.debug("test.run.1", "description", "name", "scott");
        assertTrue("test.run.1".equals(map.get("event")));

        map = LogUtil.info("test.run.1", "description", "name", "scott");
        assertTrue("test.run.1".equals(map.get("event")));

        map = LogUtil.warn("test.run.1", "description", "name", "scott");
        assertTrue("test.run.1".equals(map.get("event")));

        map = LogUtil.error("test.run.1", "description", "name", "scott");
        assertTrue("test.run.1".equals(map.get("event")));
    }

}
