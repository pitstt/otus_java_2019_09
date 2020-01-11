package ru.otus.l018;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.lang.reflect.Array;

import static ru.otus.l018.MyJsonWrapperTypes.isWrapperType;
import static ru.otus.l018.MyJsonWrapperTypes.objectToJsonValue;

public class MyJsonArray {

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
            } else if (Array.get(o, i) instanceof Boolean) {
                arrayBuilder.add((Boolean) Array.get(o, i));
            } else if (isWrapperType(Array.get(o, i).getClass())) {
                arrayBuilder.add(objectToJsonValue(Array.get(o, i)));
            }
        }
        return arrayBuilder;
    }

}
