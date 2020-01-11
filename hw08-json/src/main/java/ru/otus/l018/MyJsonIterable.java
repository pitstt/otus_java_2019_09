package ru.otus.l018;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;

import static ru.otus.l018.MyJsonArray.arrayWriter;
import static ru.otus.l018.MyJsonArray.primitiveWriter;

public class MyJsonIterable {

    public static JsonArrayBuilder iterableWriter(Object o) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        ((Iterable<?>) o).forEach(e -> {
            if (e.getClass().isArray()) {
                JsonArrayBuilder newArrayBuilder = Json.createArrayBuilder();
                for (int j = 0; j < Array.getLength(o); j++) {
                    arrayWriter(Array.get(o, j));
                }
                arrayBuilder.add(newArrayBuilder);
            } else if (e instanceof Iterable) {
                arrayBuilder.add(iterableWriter(e));
            } else if (e instanceof Integer) {
                arrayBuilder.add((Integer) e);
            } else if (e instanceof String) {
                arrayBuilder.add((String) e);
            } else if (e instanceof Double) {
                arrayBuilder.add((Double) e);
            } else if (e instanceof Boolean) {
                arrayBuilder.add((Boolean) e);
            } else if (e instanceof BigInteger) {
                arrayBuilder.add((BigInteger) e);
            } else if (e instanceof BigDecimal) {
                arrayBuilder.add((BigDecimal) e);
            } else if (e.getClass().isPrimitive()) {
                primitiveWriter(arrayBuilder, e);
            }
        });
        return arrayBuilder;
    }

}
