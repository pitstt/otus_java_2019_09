package ru.otus.lO15;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


public class RunnerTest {

    public static void run(Class<?> c) {
        Methods methods = new Methods(Arrays.asList(c.getMethods()));
        int success = 0;
        int error = 0;
        for (Method test : methods.testMethods) {
            printTestName(test.getName());
            Object testsClassInstance = null;
            try {
                testsClassInstance = c.getDeclaredConstructor().newInstance();
                try {
                    invokeMethodsWithExceptionHandling(methods.beforeTestMethods, testsClassInstance);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
                test.invoke(testsClassInstance);
                success++;
            } catch (Exception e) {
                error++;
                e.printStackTrace();
            } finally {
                try {
                    invokeMethodsWithExceptionHandling(methods.afterTestMethods, testsClassInstance);
                } catch (Exception e) {
                    e.printStackTrace();
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

    private static void printTestName(String testName) {
        System.out.println("--------------------------");
        System.out.println(testName);
        System.out.println("--------------------------");
    }

    private static void invokeMethodsWithExceptionHandling(List<Method> methodList, Object testsClassInstance) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methodList) {
            method.invoke(testsClassInstance);
        }
    }

}
