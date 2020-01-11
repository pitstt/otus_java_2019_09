package ru.otus.l018;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;

public class MyJsonArray {
    protected static void primitiveWriter(JsonArrayBuilder arrayBuilder, Object o) {
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

    public static JsonArrayBuilder arrayWriter(Object o) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < Array.getLength(o); i++) {
            if (Array.get(o, i).getClass().isArray()) {
                JsonArrayBuilder newArrayBuilder = Json.createArrayBuilder();
                for (int j = 0; j < Array.getLength(o); j++) {
                    arrayWriter(Array.get(o, j));
                }
                arrayBuilder.add(newArrayBuilder);
            } else if (Array.get(o, i) instanceof Iterable) {
                arrayBuilder.add(arrayWriter(Array.get(o, i)));
            } else if (Array.get(o, i) instanceof Integer) {
                arrayBuilder.add((Integer) Array.get(o, i));
            } else if (Array.get(o, i) instanceof String) {
                arrayBuilder.add((String) Array.get(o, i));
            } else if (Array.get(o, i) instanceof Double) {
                arrayBuilder.add((Double) Array.get(o, i));
            } else if (Array.get(o, i) instanceof Boolean) {
                arrayBuilder.add((Boolean) Array.get(o, i));
            } else if (Array.get(o, i) instanceof BigInteger) {
                arrayBuilder.add((BigInteger) Array.get(o, i));
            } else if (Array.get(o, i) instanceof BigDecimal) {
                arrayBuilder.add((BigDecimal) Array.get(o, i));
            } else if (Array.get(o, i).getClass().isPrimitive()) {
                primitiveWriter(arrayBuilder, Array.get(o, i));
            }
        }
        return arrayBuilder;
    }

}
