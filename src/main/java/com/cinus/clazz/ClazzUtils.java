package com.cinus.clazz;

import com.cinus.thirdparty.Assert;
import com.cinus.thirdparty.Constants;
import com.cinus.thirdparty.binary.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ClazzUtils {


    private static final String SUFFIX = ".class";

    private static final String CLASSES = "classes";


    public static Vector<Class<?>> getLoadedClasses(ClassLoader cl) throws NoSuchFieldException, IllegalAccessException {
        if (cl == null) {
            return null;
        }
        Field classes = ClassLoader.class.getDeclaredField(CLASSES);
        classes.setAccessible(true);
        return (Vector<Class<?>>) classes.get(cl);
    }


    public static List<Class<?>> getLoadedClassesByAnnotation(ClassLoader cl, Class<? extends Annotation> annotationClass) throws NoSuchFieldException, IllegalAccessException {
        Vector<Class<?>> classes = getLoadedClasses(cl);
        if (classes == null || classes.isEmpty()) {
            return Collections.emptyList();
        }
        return classes.stream().filter(c -> c.isAnnotationPresent(annotationClass)).collect(Collectors.toList());
    }


    public static Class<?> getClassFromName(String className) {
        Class<?> c;
        try {
            ClassLoader cl = getDefaultClassLoader();
            c = Class.forName(className, true, cl);
        } catch (ClassNotFoundException e) {
            try {
                ClassLoader cl = ClazzUtils.class.getClassLoader();
                c = Class.forName(className, true, cl);
            } catch (ClassNotFoundException e1) {
                try {
                    c = Class.forName(className);
                } catch (ClassNotFoundException e2) {
                    c = null;
                }
            }
        }
        return c;
    }


    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable t1) {
            cl = ClazzUtils.class.getClassLoader();
            if (cl != null) {
                return cl;
            }
            try {
                cl = ClassLoader.getSystemClassLoader();
            } catch (Throwable t2) {
            }
        }
        return cl;
    }


    public static Set<Class<?>> getAllSuperClass(Class<?> clazz) {
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        Set<Class<?>> classes = new LinkedHashSet<>();
        while (clazz != Object.class) {
            classes.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return classes;
    }


    public static Class<?>[] toClassArray(Collection<Class<?>> collection) {
        return (Class[]) collection.toArray(new Class[0]);
    }

    public static Class<?>[] getAllInterfaces(Object instance) {
        Assert.notNull(instance, "[Assertion failed] - instance is required; it must not be null");
        return getAllInterfacesForClass(instance.getClass());
    }

    public static Class<?>[] getAllInterfacesForClass(Class<?> clazz) {
        return getAllInterfacesForClass(clazz, null);
    }

    public static Class<?>[] getAllInterfacesForClass(Class<?> clazz, ClassLoader classLoader) {
        return toClassArray(getAllInterfacesForClassAsSet(clazz, classLoader));
    }

    public static Set<Class<?>> getAllInterfacesAsSet(Object instance) {
        Assert.notNull(instance, "[Assertion failed] - instance is required; it must not be null");
        return getAllInterfacesForClassAsSet(instance.getClass());
    }

    public static Set<Class<?>> getAllInterfacesForClassAsSet(Class<?> clazz) {
        return getAllInterfacesForClassAsSet(clazz, null);
    }

    public static Set<Class<?>> getAllInterfacesForClassAsSet(Class<?> clazz, ClassLoader classLoader) {
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        if (clazz.isInterface() && isVisible(clazz, classLoader)) {
            return Collections.singleton(clazz);
        } else {
            Set<Class<?>> interfaces = new LinkedHashSet<>();
            for (Class current = clazz; current != null; current = current.getSuperclass()) {
                Class<?>[] ifcs = current.getInterfaces();
                for (Class<?> ifc : ifcs) {
                    if (isVisible(ifc, classLoader)) {
                        interfaces.add(ifc);
                    }
                }
            }
            return interfaces;
        }
    }

    public static boolean isVisible(Class<?> clazz, ClassLoader classLoader) {
        if (classLoader == null) {
            return true;
        } else {
            try {
                if (clazz.getClassLoader() == classLoader) {
                    return true;
                }
            } catch (SecurityException e) {
            }
            return isLoadable(clazz, classLoader);
        }
    }

    private static boolean isLoadable(Class<?> clazz, ClassLoader classLoader) {
        try {
            return clazz == classLoader.loadClass(clazz.getName());
        } catch (ClassNotFoundException var3) {
            return false;
        }
    }


    public static boolean isInnerClass(Class<?> clazz) {
        return clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers());
    }

    public static String getClassFileName(Class<?> clazz) {
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        String className = clazz.getName();
        int lastDotIndex = className.lastIndexOf(46);
        return className.substring(lastDotIndex + 1) + SUFFIX;
    }

    public static String getPackageName(Class<?> clazz) {
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        return getPackageName(clazz.getName());
    }

    public static String getPackageName(String className) {
        Assert.notNull(className, "[Assertion failed] - class name is required; it must not be null");
        int lastDotIndex = className.lastIndexOf(46);
        return lastDotIndex != -1 ? className.substring(0, lastDotIndex) : "";
    }

    public static String getQualifiedName(Class<?> clazz) {
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        return clazz.getTypeName();
    }

    public static String getQualifiedMethodName(Method method) {
        return getQualifiedMethodName(method, null);
    }

    public static String getQualifiedMethodName(Method method, Class<?> clazz) {
        Assert.notNull(method, "[Assertion failed] - method is required; it must not be null");
        return (clazz != null ? clazz : method.getDeclaringClass()).getName() + '.' + method.getName();
    }

    public static boolean hasMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        return getMethodIfAvailable(clazz, methodName, paramTypes) != null;
    }

    public static Method getMethodIfAvailable(Class<?> clazz, String name, Class<?>... paramTypes) {
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        Assert.notNull(name, "[Assertion failed] - method name is required; it must not be null");
        if (paramTypes != null) {
            try {
                return clazz.getMethod(name, paramTypes);
            } catch (NoSuchMethodException var9) {
                return null;
            }
        } else {
            Set<Method> candidates = new HashSet<>(1);
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (name.equals(method.getName())) {
                    candidates.add(method);
                }
            }
            if (candidates.size() == 1) {
                return candidates.iterator().next();
            } else {
                return null;
            }
        }
    }


    public static Method getStaticMethod(Class<?> clazz, String name, Class<?>... args) {
        Assert.notNull(clazz, "[Assertion failed] - class is required; it must not be null");
        Assert.notNull(name, "[Assertion failed] - method name is required; it must not be null");
        try {
            Method method = clazz.getMethod(name, args);
            return Modifier.isStatic(method.getModifiers()) ? method : null;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }


    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(SUFFIX)) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static Class<?>[] getAllClasses(String packageName) throws ClassNotFoundException, IOException {
        Class<?> cla = ClazzUtils.class;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = cla.getClassLoader().getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    public static List<Class<?>> getAllClasses(String packageName, IClassFilter filter) throws IOException, ClassNotFoundException {
        if (StringUtils.isEmpty(packageName)) packageName = Constants.EMPTY;
        packageName = packageName.replace(Constants.STOP_EN, Constants.FILE_SEP);
        Enumeration<URL> resourceUrls = ClazzUtils.class.getClassLoader().getResources(packageName);
        List<Class<?>> classes = new ArrayList<>();

        packageName = packageName.replace(Constants.FILE_SEP, Constants.STOP_EN);
        while (resourceUrls.hasMoreElements()) {
            URL url = resourceUrls.nextElement();
            if ("file".equals(url.getProtocol())) {
                getClassesFromDir(new File(url.getFile()), Constants.EMPTY, classes, filter, packageName);
            } else {
                JarURLConnection jarCon = (JarURLConnection) url.openConnection();
                JarFile jarFile = jarCon.getJarFile();
                getClassesFromJar(jarFile, classes, filter, packageName);
            }
        }
        return classes;
    }


    private static void getClassesFromJar(JarFile jarFile, List<Class<?>> classes, IClassFilter filter, String packageName) throws ClassNotFoundException {
        Enumeration<JarEntry> entrys = jarFile.entries();
        while (entrys.hasMoreElements()) {
            String name = entrys.nextElement().getName();
            if (!name.endsWith(SUFFIX)) {
                continue;
            }
            String className = name.replace(Constants.FILE_SEP, Constants.STOP_EN).substring(0, name.length() - SUFFIX.length());
            if (StringUtils.isEmpty(packageName) || className.startsWith(packageName)) {
                Class<?> clazz = Class.forName(className);
                if (filter.filterClass(clazz)) {
                    classes.add(clazz);
                }
            }
        }
    }


    private static void getClassesFromDir(File dir, String pk, List<Class<?>> classes, IClassFilter filter, String packageName) throws ClassNotFoundException {
        if (StringUtils.isEmpty(pk)) {
            pk = packageName;
        }
        if (!pk.isEmpty() && !pk.endsWith(Constants.STOP_EN)) pk += Constants.STOP_EN;
        if (!dir.exists()) {
            return;
        }
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                getClassesFromDir(f, pk + f.getName(), classes, filter, packageName);
            }
            String name = f.getName();
            if (name.endsWith(SUFFIX)) {
                String className = pk + name.substring(0, name.length() - SUFFIX.length());
                if (StringUtils.isEmpty(packageName) || className.startsWith(packageName)) {
                    Class<?> clazz = Class.forName(className);
                    if (filter.filterClass(clazz)) {
                        classes.add(clazz);
                    }
                }
            }
        }
    }

}
