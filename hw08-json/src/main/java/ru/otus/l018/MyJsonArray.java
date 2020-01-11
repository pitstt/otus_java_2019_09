package ru.otus.l018;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.lang.reflect.Array;

public class MyJsonArray {

    public static void arrayWriter(JsonArrayBuilder arrayBuilder, Object o) {
        if (o.getClass().isArray()) {
            JsonArrayBuilder newArrayBuilder = Json.createArrayBuilder();
            for (int i = 0; i < Array.getLength(o); i++) {
                arrayWriter(arrayBuilder, Array.get(o, i));
            }
            arrayBuilder.add(newArrayBuilder);
        } else if (o instanceof Iterable) {
            JsonArrayBuilder newArrayBuilder = Json.createArrayBuilder();
            ((Iterable<?>) o).forEach(e -> {
                arrayWriter(arrayBuilder, e);
            });
            arrayBuilder.add(newArrayBuilder);
        } else if (o.getClass().equals(Integer.class)) {
            arrayBuilder.add((Integer) o);
        } else if (o.getClass().equals(String.class)) {
            arrayBuilder.add((String) o);
        } else if (o.getClass().equals(Double.class)) {
            arrayBuilder.add((Double) o);
        } else if (o.getClass().equals(Boolean.class)) {
            arrayBuilder.add((Boolean) o);
        } else if (o.getClass().isPrimitive()) {
            primitiveWriter(arrayBuilder, o);
        }
    }

    private static void primitiveWriter(JsonArrayBuilder arrayBuilder, Object o) {
        if (o.getClass().equals(int.class)) {
            arrayBuilder.add((int) o);
        } else if (o.getClass().equals(long.class)) {
            arrayBuilder.add((long) o);
        } else if (o.getClass().equals(double.class)) {
            arrayBuilder.add((double) o);
        } else if (o.getClass().equals(boolean.class)) {
            arrayBuilder.add((boolean) o);
        }
    }
}
