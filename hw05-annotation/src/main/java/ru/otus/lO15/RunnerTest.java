package ru.otus.lO15;

import ru.otus.lO15.annotation.After;
import ru.otus.lO15.annotation.Before;
import ru.otus.lO15.annotation.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RunnerTest {

    private static int success = 0;
    private static int error = 0;

    public static void run(Class<?> c) {
        List<Method> afterTestMethods = new ArrayList<>();
        List<Method> beforeTestMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        for (Method method : c.getMethods()) {
            if (method.isAnnotationPresent(After.class)) {
                afterTestMethods.add(method);
            }
            if (method.isAnnotationPresent(Before.class)) {
                beforeTestMethods.add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }

        testMethods.forEach(test -> {
            System.out.println("--------------------------");
            System.out.println(test.getName());
            System.out.println("--------------------------");
            try {
                Object testsClass = c.newInstance();
                beforeTestMethods.forEach(before -> {
                    try {
                        before.invoke(testsClass);
                    } catch (Exception e) {
                        error++;
                        e.printStackTrace();
                    }
                });
                test.invoke(testsClass);
                afterTestMethods.forEach(method -> {
                    try {
                        method.invoke(testsClass);
                    } catch (Exception e) {
                        error++;
                        e.printStackTrace();
                    }
                });
                success++;
            } catch (Exception e) {
                error++;
                e.printStackTrace();
            }
        });
        int count = error + success;
        System.out.println("--------------------------");
        System.out.println("CountTest= " + count + " ;Success= " + success + " ;Error= " + error);
        System.out.println("--------------------------");
    }

    private void runTests(List<Method> before, List<Method> after, List<Method> test) {


    }

}
