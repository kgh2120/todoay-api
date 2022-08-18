package com.todoay.api.domain.todo.utility;

import com.todoay.api.domain.todo.exception.EnumMismatchException;

public interface EnumTransformer {
    static Enum valueOfEnum(Class clazz, String name) {
        try {
            return Enum.valueOf(clazz, name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new EnumMismatchException();
        }

    }
}
