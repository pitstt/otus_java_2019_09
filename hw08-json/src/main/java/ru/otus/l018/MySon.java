package ru.otus.l018;

import javax.json.Json;

import static ru.otus.l018.MyJsonArray.arrayWriter;
import static ru.otus.l018.MyJsonObject.writerObject;
import static ru.otus.l018.MyJsonWrapperTypes.isWrapperType;

public class MySon {

    public static String toJson(Object o) {
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

}
