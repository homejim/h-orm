package com.homejim.framework.reflection.involker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Clinton Begin
 * 属性的读Invoker：因为没有相应的 getter
 */
public class GetFieldInvoker implements Invoker {
    private final Field field;

    public GetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
        return field.get(target);
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }
}
