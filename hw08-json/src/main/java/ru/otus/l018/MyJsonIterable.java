package ru.otus.l018;

import javax.json.Json;
import javax.json.JsonArrayBuilder;

import static ru.otus.l018.MyJsonSerializer.objectToJsonValue;

public class MyJsonIterable {

    public static JsonArrayBuilder iterableWriter(Object o) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        ((Iterable<?>) o).forEach(e -> {
            arrayBuilder.add(objectToJsonValue(e));
        });
        return arrayBuilder;
    }

}
