package ru.otus.lO15;

import ru.otus.lO15.annotation.After;
import ru.otus.lO15.annotation.Before;
import ru.otus.lO15.annotation.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunnerTest {

    public static void run(Class<?> c) {
        Methods methods = getMethods(Arrays.asList(c.getMethods()));
        final int[] success = {0};
        final int[] error = {0};
        methods.testMethods.forEach(test -> {
            System.out.println("--------------------------");
            System.out.println(test.getName());
            System.out.println("--------------------------");
            try {
                Object testsClass = c.newInstance();
                methods.beforeTestMethods.forEach(before -> {
                    try {
                        before.invoke(testsClass);
                    } catch (Exception e) {
                        error[0] = error[0] + (methods.testMethods.size() - error[0] - success[0]);
                        e.printStackTrace();
                        printStatistic(success[0], error[0]);
                        startAfterMethods(methods.afterTestMethods, testsClass);
                        System.exit(1);
                    }
                });

                test.invoke(testsClass);

                startAfterMethods(methods.afterTestMethods, testsClass);

                success[0]++;
            } catch (Exception e) {
                error[0]++;
                e.printStackTrace();
            }
        });
        printStatistic(success[0], error[0]);
    }

    private static void startAfterMethods(List<Method> afterMethods, Object testsClass) {
        afterMethods.forEach(method -> {
            try {
                method.invoke(testsClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void printStatistic(int success, int error) {
        int count = error + success;
        System.out.println("--------------------------");
        System.out.println("CountTest= " + count + " ;Success= " + success + " ;Error= " + error);
        System.out.println("--------------------------");
    }

    private static Methods getMethods(List<Method> methodList) {
        List<Method> afterTestMethods = new ArrayList<>();
        List<Method> beforeTestMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        for (Method method : methodList) {
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
        return new Methods(beforeTestMethods, afterTestMethods, testMethods);
    }

    private static class Methods {
        List<Method> beforeTestMethods;
        List<Method> afterTestMethods;
        List<Method> testMethods;

        Methods(List<Method> beforeTestMethods, List<Method> afterTestMethods, List<Method> testMethods) {
            this.beforeTestMethods = beforeTestMethods;
            this.afterTestMethods = afterTestMethods;
            this.testMethods = testMethods;
        }

    }

}
