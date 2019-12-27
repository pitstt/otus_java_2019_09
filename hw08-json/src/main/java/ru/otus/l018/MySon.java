package ru.otus.l018;

import com.google.gson.Gson;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MySon {

    public static void main(String[] args) throws Exception {
        TestClass testClass = new TestClass(1, "string", 1.1, true, new String[]{"s1", "s2", "s3"}, Arrays.asList("s1", "s2", "s3"));
        String mySon = create(testClass).toString();

        Gson gson = new Gson();
        String json = gson.toJson(testClass);
        System.out.println(json);
        System.out.println(mySon);
        if (!mySon.equals(json)) {
            throw new Exception("Файлы отличаются!");
        }
    }

    private static JsonObject create(Object o) {
        List<Field> fields = Arrays.stream(o.getClass().getDeclaredFields()).collect(Collectors.toList());
        var jsonObjectBuilder = Json.createObjectBuilder();
        for (Field field : fields) {
            try {
                Class<?> c = field.getType();
                if (c.getName().startsWith("[")) {
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    List.of((Object[]) field.get(o)).forEach(e -> {
                        arrayBuilder.add(e.toString());
                    });
                    jsonObjectBuilder.add(field.getName(), arrayBuilder);
                } else if (c.equals(List.class)) {
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    ((List<?>) field.get(o)).forEach(e -> {
                        arrayBuilder.add(e.toString());
                    });
                    jsonObjectBuilder.add(field.getName(), arrayBuilder);
                } else if (c.equals(Integer.class)) {
                    jsonObjectBuilder.add(field.getName(), (Integer) field.get(o));
                } else if (c.equals(String.class)) {
                    jsonObjectBuilder.add(field.getName(), (String) field.get(o));
                } else if (c.equals(Double.class)) {
                    jsonObjectBuilder.add(field.getName(), (Double) field.get(o));
                } else if (c.equals(Boolean.class)) {
                    jsonObjectBuilder.add(field.getName(), (Boolean) field.get(o));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return jsonObjectBuilder.build();
    }

}
