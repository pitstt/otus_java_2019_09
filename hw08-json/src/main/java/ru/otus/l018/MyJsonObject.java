package ru.otus.l018;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.l018.JsonWrapperTypes.isWrapperType;
import static ru.otus.l018.JsonWrapperTypes.objectToJsonValue;
import static ru.otus.l018.MyJsonArray.arrayWriter;

public class MyJsonObject {

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
                } else if (field.get(o) instanceof Boolean) {
                    jsonObjectBuilder.add(field.getName(), (boolean) field.get(o));
                } else if (isWrapperType(field.get(o).getClass())) {
                    jsonObjectBuilder.add(field.getName(), objectToJsonValue(field.get(o)));
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


}
