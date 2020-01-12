package ru.otus.l018;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.l018.MyJsonSerializer.objectToJsonValue;

public class MyJsonObject {

    protected static JsonObjectBuilder writerObject(Object o) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        List<Field> fields = Arrays.stream(o.getClass().getDeclaredFields())
                .filter(field -> field.getModifiers() != (Modifier.STATIC + Modifier.FINAL + Modifier.PUBLIC))
                .collect(Collectors.toList());
        for (Field field : fields) {
            try {
                jsonObjectBuilder.add(field.getName(), objectToJsonValue(field.get(o)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return jsonObjectBuilder;
    }

}
