package com.cinus.clazz;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


public class ClazzUtilsTest {

    @Test
    public void test_hasMethod() {
        Assert.assertEquals(ClazzUtils.getClassFromName("java.lang.String"), String.class);

        Assert.assertTrue(ClazzUtils.hasMethod(Collection.class, "size"));
        Assert.assertTrue(ClazzUtils.hasMethod(Collection.class, "remove", Object.class));

        Method method = ClazzUtils.getMethodIfAvailable(Collection.class, "size");
        Assert.assertNotNull(method);
        Assert.assertEquals(method.getName(), "size");
    }


    @Test
    public void test_getAllSuperClass() {
        Set<Class<?>> classes = ClazzUtils.getAllSuperClass(ArrayList.class);
        Assert.assertTrue(classes.contains(AbstractList.class));
    }
}
