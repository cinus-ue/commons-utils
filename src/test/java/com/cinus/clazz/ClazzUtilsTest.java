package com.cinus.clazz;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;


public class ClazzUtilsTest {

    @Test
    public void test_hasMethod() {
        assertEquals(ClazzUtils.getClassFromName("java.lang.String"), String.class);

        assertTrue(ClazzUtils.hasMethod(Collection.class, "size"));
        assertTrue(ClazzUtils.hasMethod(Collection.class, "remove", Object.class));

        Method method = ClazzUtils.getMethodIfAvailable(Collection.class, "size");
        assertNotNull(method);
        assertEquals(method.getName(), "size");
    }


    @Test
    public void test_getAllSuperClass() {
        Set<Class<?>> classes = ClazzUtils.getAllSuperClass(ArrayList.class);
        assertTrue(classes.contains(AbstractList.class));
    }
}
