package com.cinus.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class CollectionUtilsTest {
    @Test
    public void test_collection() {
        Assert.assertTrue(CollectionUtils.sub(Arrays.asList("A", "B", "C", "D"), 0, 2).size() == 2);
        Assert.assertEquals(CollectionUtils.sub(Arrays.asList("A", "B", "C", "D"), 0, 1).get(0), "A");
        Assert.assertEquals(CollectionUtils.join(Arrays.asList("A", "B", "C", "D"), ","), "A,B,C,D");
    }
}
