package ru.otus.l018;

import javax.json.Json;
import javax.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;

import static ru.otus.l018.MyJsonArray.arrayWriter;
import static ru.otus.l018.MyJsonObject.writerObject;

public class MyJsonSerializer {

    public static JsonValue objectToJsonValue(Object o) {
        if (o == null) {
            return JsonValue.NULL;
        } else if (o instanceof Integer) {
            return Json.createValue((Integer) o);
        } else if (o instanceof String) {
            return Json.createValue((String) o);
        } else if (o instanceof BigInteger) {
            return Json.createValue((BigInteger) o);
        } else if (o instanceof BigDecimal) {
            return Json.createValue((BigDecimal) o);
        } else if (o instanceof Double) {
            return Json.createValue((Double) o);
        } else if (o instanceof Character) {
            return Json.createValue(o.toString());
        } else if (o instanceof Byte) {
            return Json.createValue((Byte) o);
        } else if (o instanceof Long) {
            return Json.createValue((Long) o);
        } else if (o instanceof Float) {
            return Json.createValue((Float) o);
        } else if (o.getClass().isArray()) {
            return arrayWriter(o).build();
        } else if (o instanceof Iterable) {
            return MyJsonIterable.iterableWriter(o).build();
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? JsonValue.TRUE : JsonValue.FALSE;
        } else {
            return writerObject(o).build();
        }
    }

}
