package com.cinus.system;


import com.cinus.thirdparty.binary.StringUtils;

import java.io.File;


public class SystemUtils {


    public final static String JAVA_SPECIFICATION_NAME = "java.specification.name";
    public final static String JAVA_VERSION = "java.version";
    public final static String JAVA_SPECIFICATION_VERSION = "java.specification.version";
    public final static String JAVA_VENDOR = "java.vendor";
    public final static String JAVA_SPECIFICATION_VENDOR = "java.specification.vendor";
    public final static String JAVA_VENDOR_URL = "java.vendor.url";
    public final static String JAVA_HOME = "java.home";
    public final static String JAVA_LIBRARY_PATH = "java.library.path";
    public final static String JAVA_TMPDIR = "java.io.tmpdir";
    public final static String JAVA_COMPILER = "java.compiler";
    public final static String JAVA_EXT_DIRS = "java.ext.dirs";


    public final static String JAVA_VM_NAME = "java.vm.name";
    public final static String JAVA_VM_SPECIFICATION_NAME = "java.vm.specification.name";
    public final static String JAVA_VM_VERSION = "java.vm.version";
    public final static String JAVA_VM_SPECIFICATION_VERSION = "java.vm.specification.version";
    public final static String JAVA_VM_VENDEOR = "java.vm.vendor";
    public final static String JAVA_VM_SPECIFICATION_VENDOR = "java.vm.specification.vendor";


    public final static String JAVA_CLASS_VERSION = "java.class.version";
    public final static String JAVA_CLASS_PATH = "java.class.path";

    public final static String OS_NAME = "os.name";
    public final static String OS_ARCH = "os.arch";
    public final static String OS_VERSION = "os.version";
    public final static String FILE_SEPRATOR = "file.separator";
    public final static String PATH_SEPRATOR = "path.separator";
    public final static String LINE_SEPRATOR = "line.separator";


    public final static String USER_NAME = "user.name";
    public final static String USER_HOME = "user.home";
    public final static String USER_DIR = "user.dir";


    public static String getSystemProperty(String property) {
        String value = System.getProperty(property);
        return StringUtils.isEmpty(value) ? null : value;
    }


    public static String getEnvironmentVariable(String name) {
        String value = System.getenv(name);
        return StringUtils.isEmpty(value) ? null : value;
    }


    public static String getSystemVariable(String var) {
        String value = getSystemProperty(var);
        if (StringUtils.isEmpty(value)) {
            value = getEnvironmentVariable(var);
        }
        return StringUtils.isEmpty(value) ? null : value;
    }

    public static File getUserDir() {
        return new File(getSystemProperty(USER_DIR));
    }

}
