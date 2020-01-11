package ru.otus.l018;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.otus.l018.MyJsonArray.arrayWriter;

public class MyJsonObject {

    private static final Set<Class> WRAPPER_TYPES = new HashSet(Arrays.asList(
            Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class));

    public static String create(Object o) {
        var jsonObjectBuilder = Json.createObjectBuilder();
        if (o.getClass().isArray()) {
            return arrayWriter(o).build().toString();
        } else if (o instanceof Iterable) {
            return MyJsonIterable.iterableWriter(o).build().toString();
        } else if (o.getClass().isPrimitive()) {
            return (String) o;
        } else if (isWrapperType(o.getClass())) {
            return o.toString();
        } else {
            writerObject(jsonObjectBuilder, o);
            return jsonObjectBuilder.build().toString();
        }
    }

    private static void writerObject(JsonObjectBuilder jsonObjectBuilder, Object o) {
        List<Field> fields = Arrays.stream(o.getClass().getDeclaredFields()).collect(Collectors.toList());
        for (Field field : fields) {
            try {
                Class<?> c = field.getType();
                if (c.isArray()) {
                    jsonObjectBuilder.add(field.getName(), arrayWriter(field.get(o)));
                } else if (field.get(o) instanceof Iterable) {
                    jsonObjectBuilder.add(field.getName(), MyJsonIterable.iterableWriter(field.get(o)));
                } else if (field.get(o) instanceof Integer) {
                    jsonObjectBuilder.add(field.getName(), (Integer) field.get(o));
                } else if (field.get(o) instanceof String) {
                    jsonObjectBuilder.add(field.getName(), (String) field.get(o));
                } else if (field.get(o) instanceof Double) {
                    jsonObjectBuilder.add(field.getName(), (Double) field.get(o));
                } else if (field.get(o) instanceof Boolean) {
                    jsonObjectBuilder.add(field.getName(), (Boolean) field.get(o));
                } else if (field.get(o) instanceof BigInteger) {
                    jsonObjectBuilder.add(field.getName(), (BigInteger) field.get(o));
                } else if (field.get(o) instanceof BigDecimal) {
                    jsonObjectBuilder.add(field.getName(), (BigDecimal) field.get(o));
                } else if (c.isPrimitive()) {
                    primitiveWriter(jsonObjectBuilder, field, o);
                } else {
                    var newJsonObjectBuilder = Json.createObjectBuilder();
                    writerObject(newJsonObjectBuilder, field.get(o));
                    jsonObjectBuilder.add(field.getName(), newJsonObjectBuilder);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void primitiveWriter(JsonObjectBuilder jsonObjectBuilder, Field field, Object o) throws IllegalAccessException {
        if (field.getType().equals(int.class)) {
            jsonObjectBuilder.add(field.getName(), (int) field.get(o));
        } else if (field.getType().equals(long.class)) {
            jsonObjectBuilder.add(field.getName(), (long) field.get(o));
        } else if (field.getType().equals(double.class)) {
            jsonObjectBuilder.add(field.getName(), (double) field.get(o));
        } else if (field.getType().equals(boolean.class)) {
            jsonObjectBuilder.add(field.getName(), (boolean) field.get(o));
        }
    }

    private static boolean isWrapperType(Class clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

}
