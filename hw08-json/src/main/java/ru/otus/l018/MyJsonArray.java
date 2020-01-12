package ru.otus.l018;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.lang.reflect.Array;

import static ru.otus.l018.MyJsonSerializer.objectToJsonValue;

public class MyJsonArray {

    public static JsonArrayBuilder arrayWriter(Object o) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < Array.getLength(o); i++) {
            arrayBuilder.add(objectToJsonValue(Array.get(o, i)));
        }
        return arrayBuilder;
    }

}
