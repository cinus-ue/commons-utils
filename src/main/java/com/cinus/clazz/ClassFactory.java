package com.cinus.clazz;

import com.cinus.array.ArrayUtils;
import com.cinus.exception.UtilException;
import com.cinus.thirdparty.Assert;

public class ClassFactory<T> {

    private Class<T> clazz;
    private Class<?>[] types;
    private Object[] params;

    public ClassFactory() {
    }

    public ClassFactory(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ClassFactory<T> clazz(Class<T> clazz) {
        this.clazz = clazz;
        return this;
    }

    public ClassFactory<T> types(Class<?>... types) {
        this.types = types;
        return this;
    }

    public ClassFactory<T> params(Object... params) {
        this.params = params;
        return this;
    }

    public T newInstance() {
        Assert.notNull(clazz, "[Assertion failed] - clazz is required; it must not be null");
        try {
            if (ArrayUtils.isEmpty(params)) {
                return clazz.newInstance();
            }
            if (ArrayUtils.isEmpty(types)) {
                types = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    types[i] = params[i].getClass();
                }
            }
            return clazz.getConstructor(types).newInstance(params);
        } catch (Throwable t) {
            throw new UtilException(t);
        }
    }

}
