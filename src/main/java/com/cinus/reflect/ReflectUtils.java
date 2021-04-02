package com.cinus.reflect;

import com.cinus.array.ArrayUtils;
import com.cinus.thirdparty.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ReflectUtils {

    private static final Map<Class<?>, Field[]> DECLARED_FIELDS_CACHE = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Method[]> DECLARED_METHODS_CACHE = new ConcurrentHashMap<>();


    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, null);
    }

    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        Assert.notNull(name, "[Assertion failed] - field name is required; it must not be null");
        Assert.isTrue(name != null || type != null, "Either name or type of the field must be specified");
        for (Class searchType = clazz; Object.class != searchType && searchType != null; searchType = searchType.getSuperclass()) {
            Field[] fields = getDeclaredFields(searchType);
            int length = fields.length;
            for (int i = 0; i < length; ++i) {
                Field field = fields[i];
                if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
        }
        return null;
    }

    public static void setFieldValue(Object instance, String name, Object value) throws IllegalAccessException {
        Assert.notNull(instance, "[Assertion failed] - instance is required; it must not be null");
        Assert.notNull(name, "[Assertion failed] - field name is required; it must not be null");
        Assert.notNull(value, "[Assertion failed] - value is required; it must not be null");
        Class clazz = instance.getClass();
        Field field = findField(clazz, name);
        setFieldValue(field, instance, value);
    }

    public static void setFieldValue(Field field, Object instance, Object value) throws IllegalAccessException {
        Assert.notNull(field, "[Assertion failed] - field is required; it must not be null");
        Assert.notNull(instance, "[Assertion failed] - instance is required; it must not be null");
        Assert.notNull(value, "[Assertion failed] - value is required; it must not be null");
        makeAccessible(field);
        field.set(instance, value);
    }

    public static Object getFieldValue(Object instance, String name) throws IllegalAccessException {
        Assert.notNull(instance, "[Assertion failed] - instance is required; it must not be null");
        Assert.notNull(name, "[Assertion failed] - field name is required; it must not be null");
        Class clazz = instance.getClass();
        Field field = findField(clazz, name);
        return getFieldValue(field, instance);
    }

    public static Object getFieldValue(Field field, Object instance) throws IllegalAccessException {
        Assert.notNull(instance, "[Assertion failed] - field is required; it must not be null");
        Assert.notNull(instance, "[Assertion failed] - object is required; it must not be null");
        makeAccessible(field);
        return field.get(instance);
    }

    public static Method findMethod(Class<?> clazz, String name) {
        return findMethod(clazz, name, null);
    }

    public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        Assert.notNull(name, "[Assertion failed] - method name is required; it must not be null");
        for (Class searchType = clazz; searchType != null; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.isInterface() ? searchType.getMethods() : getDeclaredMethods(searchType);
            int length = methods.length;
            for (int i = 0; i < length; ++i) {
                Method method = methods[i];
                if (name.equals(method.getName()) && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
                    return method;
                }
            }
        }
        return null;
    }

    public static Object invokeMethod(Object instance, String name, Object... args) throws InvocationTargetException, IllegalAccessException {
        Assert.notNull(instance, "[Assertion failed] - instance is required; it must not be null");
        Assert.notNull(name, "[Assertion failed] - method name is required; it must not be null");
        Method method = findMethod(instance.getClass(), name);
        return invokeMethod(instance, method, args);
    }

    public static Object invokeMethod(Object instance, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        Assert.notNull(instance, "[Assertion failed] - instance is required; it must not be null");
        Assert.notNull(method, "[Assertion failed] - method is required; it must not be null");
        return method.invoke(instance, args);
    }

    public static void makeAccessible(Field field) {
        if (field != null && !field.isAccessible() && (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers()))) {
            field.setAccessible(true);
        }
    }

    public static void makeAccessible(Method method) {
        if (method != null && !method.isAccessible() && (!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))) {
            method.setAccessible(true);
        }
    }

    public static Field[] getDeclaredFields(Class<?> clazz) {
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        Field[] result = DECLARED_FIELDS_CACHE.get(clazz);
        if (result == null) {
            result = clazz.getDeclaredFields();
            DECLARED_FIELDS_CACHE.put(clazz, result != null ? result : new Field[0]);
        }
        return result;
    }

    public static Method[] getDeclaredMethods(Class<?> clazz) {
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        Method[] result = DECLARED_METHODS_CACHE.get(clazz);
        if (result == null) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            Method[] defaultMethods = findConcreteMethodsOnInterfaces(clazz);
            result = ArrayUtils.addAll(declaredMethods, defaultMethods);
            DECLARED_METHODS_CACHE.put(clazz, result != null ? result : new Method[0]);
        }
        return result;
    }

    private static Method[] findConcreteMethodsOnInterfaces(Class<?> clazz) {
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        List<Method> foundMethods = new LinkedList<>();
        for (Class<?> ifc : clazz.getInterfaces()) {
            for (Method method : ifc.getMethods()) {
                if (!Modifier.isAbstract(method.getModifiers())) {
                    foundMethods.add(method);
                }
            }
        }
        return foundMethods.toArray(new Method[foundMethods.size()]);
    }
}
