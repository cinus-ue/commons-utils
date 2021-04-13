package com.cinus.enumeration;

import com.cinus.enumeration.sample.Color;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class EnumUtilsTest {


    @Test
    public void test_enum() {
        Assert.assertTrue(Color.RED.compareTo(EnumUtils.getEnum(Color.class, "RED")) == 0);
        Map<String, Color> map = EnumUtils.getEnumMap(Color.class);
        Assert.assertTrue(map.size() == 3);
        List<Color> list = EnumUtils.getEnumList(Color.class);
        list.forEach(c -> Assert.assertTrue(map.containsKey(c.name())));
    }
}
