package com.cinus.reflect;

import com.cinus.reflect.sample.TestObject;
import com.cinus.thirdparty.binary.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ReflectUtilsTest {

    @Test
    public void test_field() throws IllegalAccessException {
        Field field = ReflectUtils.findField(TestObject.class, "name", String.class);
        Assert.assertNotNull(field);
        Assert.assertEquals(field.getName(), "name");
        Assert.assertEquals(field.getType(), String.class);

        TestObject object = new TestObject();
        ReflectUtils.setFieldValue(object, "name", "test");
        Assert.assertEquals("test", object.getName());
        String name = (String) ReflectUtils.getFieldValue(object, "name");
        Assert.assertEquals(object.getName(), name);
    }

    @Test
    public void test_invokeMethod() throws IllegalAccessException, InvocationTargetException {
        TestObject object = new TestObject();
        ReflectUtils.setFieldValue(object, "name", "test");
        String name = (String) ReflectUtils.invokeMethod(object, "getName", null);
        Assert.assertEquals("test", name);
    }

    @Test
    public void test_newInstance() {
        String a = ReflectUtils.newInstance("java.lang.String", "A");
        Assert.assertEquals("A", a);
        String b = ReflectUtils.newInstance(String.class, "B");
        Assert.assertEquals("B", b);
        String c = ReflectUtils.newInstance(String.class);
        Assert.assertTrue(StringUtils.isEmpty(c));
    }
}
