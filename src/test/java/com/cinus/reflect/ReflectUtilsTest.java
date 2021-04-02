package com.cinus.reflect;

import com.cinus.reflect.sample.TestObject;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ReflectUtilsTest {

    @Test
    public void test_field() throws IllegalAccessException, InvocationTargetException {
        Field field = ReflectUtils.findField(TestObject.class, "name", String.class);
        assertNotNull(field);
        assertEquals(field.getName(), "name");
        assertEquals(field.getType(), String.class);

        TestObject object = new TestObject();
        ReflectUtils.setFieldValue(object, "name", "test");
        assertEquals("test", object.getName());
        String name = (String) ReflectUtils.getFieldValue(object, "name");
        assertEquals(object.getName(), name);
    }

    @Test
    public void test_invokeMethod() throws IllegalAccessException, InvocationTargetException {
        TestObject object = new TestObject();
        ReflectUtils.setFieldValue(object, "name", "test");
        String name = (String) ReflectUtils.invokeMethod(object, "getName", null);
        assertEquals("test", name);
    }
}
