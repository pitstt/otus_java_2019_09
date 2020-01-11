package ru.otus.l018;

import com.google.gson.Gson;

import java.util.Arrays;

public class MySonDemo {

    public static void main(String[] args) throws Exception {
        TestClass testClass = new TestClass(1, "string", 1.1, true, new int[]{1, 2, 3}, Arrays.asList("s1", "s2", "s3"));
        String mySon = MyJsonObject.create(testClass).toString();

        Gson gson = new Gson();
        String json = gson.toJson(testClass);
        System.out.println(json);
        System.out.println(mySon);
        if (!mySon.equals(json)) {
            throw new Exception("Файлы отличаются!");
        }
    }
}

