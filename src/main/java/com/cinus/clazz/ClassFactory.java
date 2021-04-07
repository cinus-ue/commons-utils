package com.cinus.clazz;

import com.cinus.array.ArrayUtils;
import com.cinus.exception.ExceptionUtils;
import com.cinus.thirdparty.Assert;

public class ClassFactory<T> {

    private Class<T> clazz;
    private Class<?>[] types;
    private Object[] args;

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

    public ClassFactory<T> params(Object... args) {
        this.args = args;
        return this;
    }

    public T newInstance() {
        Assert.notNull(clazz, "[Assertion failed] - clazz is required; it must not be null");
        try {
            if (ArrayUtils.isEmpty(args)) return clazz.newInstance();
            if (ArrayUtils.isEmpty(types)) {
                types = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    types[i] = args[i].getClass();
                }
            }
            return clazz.getConstructor(types).newInstance(args);
        } catch (Exception e) {
            throw ExceptionUtils.utilException(e);
        }
    }

}
