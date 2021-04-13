package com.cinus.enumeration;

import com.cinus.thirdparty.binary.StringUtils;

import java.util.*;

public class EnumUtils {


    public static <E extends Enum<E>> Map<String, E> getEnumMap(Class<E> enumClass) {
        Map<String, E> map = new LinkedHashMap();
        E[] arr = enumClass.getEnumConstants();
        for (int i = 0; i < arr.length; ++i) {
            E e = arr[i];
            map.put(e.name(), e);
        }
        return map;
    }


    public static <E extends Enum<E>> List<E> getEnumList(Class<E> enumClass) {
        return new ArrayList(Arrays.asList(enumClass.getEnumConstants()));
    }

    public static <E extends Enum<E>> E getEnum(Class<E> enumClass, String enumName) {
        if (StringUtils.isEmpty(enumName)) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, enumName);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
