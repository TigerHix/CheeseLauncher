package me.cheesy.cheeselauncher.utils;

import java.lang.reflect.Field;

public class Utils {
    /**
     * Migrated from BlockLauncher.
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getDeclaredFieldRecursive(Class<?> clazz, String fieldName) {
        if (clazz == null) return null;
        try {
            Field gottenField = clazz.getDeclaredField(fieldName);
            return gottenField;
        } catch (NoSuchFieldException exception) {}
        return getDeclaredFieldRecursive(clazz.getSuperclass(), fieldName);
    }
}
