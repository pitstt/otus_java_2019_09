package ru.otus.l018;

import com.google.gson.Gson;

import java.util.Arrays;

public class MySonDemo {

    public static void main(String[] args) throws Exception {
        TestClass testClass = new TestClass(1, "string", 1.1, true, new int[]{1, 2, 3}, Arrays.asList("s1", "s2", "s3"));
        Gson gson = new Gson();
        String json = gson.toJson(testClass);
        String mySon = MyJsonObject.create(testClass);

        System.out.println(json);
        System.out.println(mySon);

        String json2 = gson.toJson(new int[]{1, 2, 3});
        String mySon2 = MyJsonObject.create(new int[]{1, 2, 3});

        System.out.println(json2);
        System.out.println(mySon2);

        String json3 = gson.toJson(Arrays.asList("s1", "s2", "s3"));
        String mySon3 = MyJsonObject.create(Arrays.asList("s1", "s2", "s3"));

        System.out.println(json3);
        System.out.println(mySon3);

        Integer i = 2;
        String json4 = gson.toJson(i);
        String mySon4 = MyJsonObject.create(i);

        System.out.println(json4);
        System.out.println(mySon4);

        int i2 = 2;
        String json5 = gson.toJson(i2);
        String mySon5 = MyJsonObject.create(i2);

        System.out.println(json5);
        System.out.println(mySon5);

        if (!mySon.equals(json)) {
            throw new Exception("Файлы отличаются!");
        }
        if (!mySon2.equals(json2)) {
            throw new Exception("Файлы отличаются!");
        }
        if (!mySon3.equals(json3)) {
            throw new Exception("Файлы отличаются!");
        }
        if (!mySon4.equals(json4)) {
            throw new Exception("Файлы отличаются!");
        }
        if (!mySon5.equals(json5)) {
            throw new Exception("Файлы отличаются!");
        }
    }
}

