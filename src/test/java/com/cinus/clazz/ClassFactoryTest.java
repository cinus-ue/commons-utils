package com.cinus.clazz;

import org.junit.Assert;
import org.junit.Test;


public class ClassFactoryTest {

    @Test
    public void test_newInstance() {
        Assert.assertEquals("", new ClassFactory<>(String.class).newInstance());

        ClassFactory<String> cf = new ClassFactory<String>().clazz(String.class).params("test");
        Assert.assertEquals("test", cf.newInstance());

        Assert.assertTrue(1 == new ClassFactory<Integer>().clazz(Integer.class).params("1").newInstance());
        Assert.assertTrue(1 == new ClassFactory<Integer>().clazz(Integer.class).params(1).types(int.class).newInstance());

    }
}
