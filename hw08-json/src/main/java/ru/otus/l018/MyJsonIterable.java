package ru.otus.l018;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.lang.reflect.Array;

import static ru.otus.l018.MyJsonWrapperTypes.isWrapperType;
import static ru.otus.l018.MyJsonWrapperTypes.objectToJsonValue;
import static ru.otus.l018.MyJsonArray.arrayWriter;

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
            } else if (e instanceof Boolean) {
                arrayBuilder.add((Boolean) e);
            } else if (isWrapperType(e.getClass())) {
                arrayBuilder.add(objectToJsonValue(e));
            }
        });
        return arrayBuilder;
    }

}
