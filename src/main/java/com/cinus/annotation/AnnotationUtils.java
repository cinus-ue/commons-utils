package com.cinus.annotation;

import com.cinus.reflect.ReflectUtils;
import com.cinus.thirdparty.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public abstract class AnnotationUtils {


    static final String VALUE = "value";


    public static List<Field> findFields(Class<? extends Annotation> annotationType, Class<?> clazz) {
        Assert.notNull(annotationType, "[Assertion failed] - annotation type is required; it must not be null");
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        List<Field> result = new LinkedList<>();
        Class<?> targetClass = clazz;
        while (targetClass != null && !Object.class.equals(targetClass)) {
            for (Field field : ReflectUtils.getDeclaredFields(targetClass)) {
                if (getAnnotation(annotationType, field) != null) {
                    result.add(field);
                }
            }
            targetClass = targetClass.getSuperclass();
        }
        return result;
    }


    public static List<Method> findMethods(Class<? extends Annotation> annotationType, Class<?> clazz) {
        Assert.notNull(annotationType, "[Assertion failed] - annotation type is required; it must not be null");
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        List<Method> result = new LinkedList<>();
        Class<?> targetClass = clazz;
        while (targetClass != null && !Object.class.equals(targetClass)) {
            for (Method method : ReflectUtils.getDeclaredMethods(targetClass)) {
                if (getAnnotation(annotationType, method) != null) {
                    result.add(method);
                }
            }
            targetClass = targetClass.getSuperclass();
        }
        return result;
    }

    public static <T extends Annotation> T getAnnotation(Class<T> annotationType, AnnotatedElement ae) {
        T ann = ae.getAnnotation(annotationType);
        if (ann == null) {
            for (Annotation metaAnn : ae.getAnnotations()) {
                ann = metaAnn.annotationType().getAnnotation(annotationType);
                if (ann != null) {
                    break;
                }
            }
        }
        return ann;
    }


    public static <T extends Annotation> T findAnnotation(Class<T> annotationType, Class<?> clazz) {
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        T annotation = clazz.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }
        for (Class<?> ifc : clazz.getInterfaces()) {
            annotation = findAnnotation(annotationType, ifc);
            if (annotation != null) {
                return annotation;
            }
        }
        if (!Annotation.class.isAssignableFrom(clazz)) {
            for (Annotation ann : clazz.getAnnotations()) {
                annotation = findAnnotation(annotationType, ann.annotationType());
                if (annotation != null) {
                    return annotation;
                }
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null || superClass.equals(Object.class)) {
            return null;
        }
        return findAnnotation(annotationType, superClass);
    }


    public static Class<?> findAnnotationDeclaringClass(Class<? extends Annotation> annotationType, Class<?> clazz) {
        Assert.notNull(annotationType, "[Assertion failed] - annotation type is required; it must not be null");
        if (clazz == null || clazz.equals(Object.class)) {
            return null;
        }
        return (isAnnotationDeclaredLocally(annotationType, clazz)) ? clazz : findAnnotationDeclaringClass(
                annotationType, clazz.getSuperclass());
    }


    public static Class<?> findAnnotationDeclaringClassForTypes(List<Class<? extends Annotation>> annotationTypes, Class<?> clazz) {
        Assert.notEmpty(annotationTypes, "[Assertion failed] - The list of annotation types must not be empty");
        if (clazz == null || clazz.equals(Object.class)) {
            return null;
        }
        for (Class<? extends Annotation> annotationType : annotationTypes) {
            if (isAnnotationDeclaredLocally(annotationType, clazz)) {
                return clazz;
            }
        }
        return findAnnotationDeclaringClassForTypes(annotationTypes, clazz.getSuperclass());
    }


    public static boolean isAnnotationDeclaredLocally(Class<? extends Annotation> annotationType, Class<?> clazz) {
        Assert.notNull(annotationType, "[Assertion failed] - annotation type is required; it must not be null");
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        boolean declaredLocally = false;
        for (Annotation annotation : clazz.getDeclaredAnnotations()) {
            if (annotation.annotationType().equals(annotationType)) {
                declaredLocally = true;
                break;
            }
        }
        return declaredLocally;
    }


    public static boolean isAnnotationInherited(Class<? extends Annotation> annotationType, Class<?> clazz) {
        Assert.notNull(annotationType, "[Assertion failed] - annotation type is required; it must not be null");
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        return (clazz.isAnnotationPresent(annotationType) && !isAnnotationDeclaredLocally(annotationType, clazz));
    }


    public static Object getValue(Annotation annotation) {
        return getValue(annotation, VALUE);
    }


    public static Object getValue(Annotation annotation, String attributeName) {
        try {
            Method method = annotation.annotationType().getDeclaredMethod(attributeName);
            return method.invoke(annotation);
        } catch (Exception ex) {
            return null;
        }
    }


    public static Object getDefaultValue(Annotation annotation) {
        return getDefaultValue(annotation, VALUE);
    }


    public static Object getDefaultValue(Annotation annotation, String attributeName) {
        return getDefaultValue(annotation.annotationType(), attributeName);
    }


    public static Object getDefaultValue(Class<? extends Annotation> annotationType) {
        return getDefaultValue(annotationType, VALUE);
    }


    public static Object getDefaultValue(Class<? extends Annotation> annotationType, String attributeName) {
        try {
            Method method = annotationType.getDeclaredMethod(attributeName);
            return method.getDefaultValue();
        } catch (Exception ex) {
            return null;
        }
    }

}