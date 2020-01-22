package ru.otus.l019.api.model;


import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ObjectSerializer {

    private  static SoftReference<Map<Class, TableObject>> cache = new SoftReference(new HashMap<Class, TableObject>());

    public static TableObject toTableObject(Object o) {
        TableObject tableObject = cache.get().get(o);
        if (tableObject != null) {
            return tableObject;
        }
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
        cache.get().put(o.getClass(), newTableObject);
        return newTableObject;
    }
}

