package ru.otus.l018;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyJsonObject {

    public static JsonObject create(Object o) {
        var jsonObjectBuilder = Json.createObjectBuilder();
        writerObject(jsonObjectBuilder, o);
        return jsonObjectBuilder.build();
    }

    private static void writerObject(JsonObjectBuilder jsonObjectBuilder, Object o) {
        List<Field> fields = Arrays.stream(o.getClass().getDeclaredFields()).collect(Collectors.toList());
        for (Field field : fields) {
            try {
                Class<?> c = field.getType();
                if (c.isArray()) {
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    for (int i = 0; i < Array.getLength(field.get(o)); i++) {
                        MyJsonArray.arrayWriter(arrayBuilder, Array.get(field.get(o), i));
                    }
                    jsonObjectBuilder.add(field.getName(), arrayBuilder);
                } else if (field.get(o) instanceof Iterable) {
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    ((Iterable<?>) field.get(o)).forEach(e -> {
                        MyJsonArray.arrayWriter(arrayBuilder, e);
                    });
                    jsonObjectBuilder.add(field.getName(), arrayBuilder);
                } else if (c.equals(Integer.class)) {
                    jsonObjectBuilder.add(field.getName(), (Integer) field.get(o));
                } else if (c.equals(String.class)) {
                    jsonObjectBuilder.add(field.getName(), (String) field.get(o));
                } else if (c.equals(Double.class)) {
                    jsonObjectBuilder.add(field.getName(), (Double) field.get(o));
                } else if (c.equals(Boolean.class)) {
                    jsonObjectBuilder.add(field.getName(), (Boolean) field.get(o));
                } else if (c.equals(BigInteger.class)) {
                    jsonObjectBuilder.add(field.getName(), (BigInteger) field.get(o));
                } else if (c.equals(BigDecimal.class)) {
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

}
