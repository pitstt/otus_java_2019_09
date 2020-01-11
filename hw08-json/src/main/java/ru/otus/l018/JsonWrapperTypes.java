package ru.otus.l018;

import javax.json.Json;
import javax.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JsonWrapperTypes {

    private static final Set<Class> WRAPPER_TYPES = new HashSet(Arrays.asList(String.class,
            Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class));

    public static JsonValue objectToJsonValue(Object o) {
        if (o instanceof Integer) {
            return Json.createValue((Integer) o);
        } else if (o instanceof String) {
            return Json.createValue((String) o);
        } else if (o instanceof BigInteger) {
            return Json.createValue((BigInteger) o);
        } else if (o instanceof BigDecimal) {
            return Json.createValue((BigDecimal) o);
        } else if (o instanceof Double) {
            return Json.createValue((Double) o);
        } else if (o instanceof Character) {
            return Json.createValue((Character) o);
        } else if (o instanceof Byte) {
            return Json.createValue((Byte) o);
        } else if (o instanceof Long) {
            return Json.createValue((Long) o);
        } else if (o instanceof Float) {
            return Json.createValue((Float) o);
        }
        return null;
    }

    public static boolean isWrapperType(Class clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }
}
