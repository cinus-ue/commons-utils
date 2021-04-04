package com.cinus.math;


import org.junit.Assert;
import org.junit.Test;

public class MathUtilsTest {

    @Test
    public void test_maximum() {
        long a = MathUtils.maximum(1, 3, 5, 7, 4, 9);
        Assert.assertTrue(a == 9);
    }

    @Test
    public void test_minimum() {
        long b = MathUtils.minimum(1, 3, 5, 7, 4, 9);
        Assert.assertTrue(b == 1);
    }

}
