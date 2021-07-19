package com.fri01.xiaozhi.model;

import java.lang.reflect.Field;

/**
 * <p>
 *     This interface contains default function to judge if a model from web
 *     has null pointer attribute or empty strings.
 * </p>
 *
 * @author forever518
 * @since 2021-05-04
 */
public interface HasNullAttribute {

    default boolean hasNullAttribute() throws IllegalAccessException {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(this) == null) {
                return true;
            } else if (field.getType() == String.class && ((String) field.get(this)).isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
