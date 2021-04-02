package com.cinus.date;


import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateUtilsTest {

    @Test
    public void test_format() {
        long timestamp = 1559437821000L;
        Date date = DateUtils.date(timestamp);
        String str = DateUtils.format(date, DateUtils.NORM_DATETIME_PATTERN);
        assertEquals(str, "2019-06-02 09:10:21");
    }
}
