package ru.otus.l018;

import static ru.otus.l018.MyJsonSerializer.objectToJsonValue;

public class MySon {

    public static String toJson(Object o) {
        return objectToJsonValue(o).toString();
    }

}
