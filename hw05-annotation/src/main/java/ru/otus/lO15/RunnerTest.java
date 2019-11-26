package ru.otus.lO15;

import java.lang.reflect.Method;
import java.util.Arrays;


public class RunnerTest {

    public static void run(Class<?> c) {
        Methods methods = new Methods(Arrays.asList(c.getMethods()));
        int success = 0;
        int error = 0;
        for (Method test : methods.testMethods) {
            System.out.println("--------------------------");
            System.out.println(test.getName());
            System.out.println("--------------------------");
            Object testsClass = null;
            try {
                testsClass = c.newInstance();
                for (Method before : methods.beforeTestMethods) {
                    try {
                        before.invoke(testsClass);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                test.invoke(testsClass);
                success++;
            } catch (Exception e) {
                error++;
                e.printStackTrace();
            } finally {
                for (Method method : methods.afterTestMethods) {
                    try {
                        method.invoke(testsClass);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        printStatistic(success, error);
    }

    private static void printStatistic(int success, int error) {
        int count = error + success;
        System.out.println("--------------------------");
        System.out.println("CountTest= " + count + " ;Success= " + success + " ;Error= " + error);
        System.out.println("--------------------------");
    }

}
