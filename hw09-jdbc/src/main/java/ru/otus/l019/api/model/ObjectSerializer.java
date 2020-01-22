package ru.otus.l019.api.model;


import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ObjectSerializer {

    private static Map<Class, TableObject> hash = new HashMap<>();

    public static TableObject toTableObject(Object o) {
        String tableName = o.getClass().getSimpleName();
        List<TableObject.Column> columns = new ArrayList<>();
        List<Field> fields = Arrays.stream(o.getClass().getDeclaredFields()).collect(Collectors.toList());
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                columns.add(new TableObject.Column(field.isAnnotationPresent(Id.class), field.getName(), String.valueOf(field.get(o))));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        TableObject newTableObject = new TableObject(tableName, columns);
        hash.put(o.getClass(), newTableObject);
        return newTableObject;
    }

    public static TableObject toTableClazz(Class c) {
        TableObject tableObject = hash.get(c);
        if (tableObject != null) {
            return tableObject;
        }
        String tableName = c.getSimpleName();
        List<TableObject.Column> columns = new ArrayList<>();
        List<Field> fields = Arrays.stream(c.getDeclaredFields()).collect(Collectors.toList());
        for (Field field : fields) {
            field.setAccessible(true);
            columns.add(new TableObject.Column(field.isAnnotationPresent(Id.class), field.getName(), null));
        }
        return new TableObject(tableName, columns);
    }

}

