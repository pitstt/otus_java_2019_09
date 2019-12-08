package ru.otus.lO15;

import ru.otus.lO15.annotation.After;
import ru.otus.lO15.annotation.Before;
import ru.otus.lO15.annotation.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;


class Methods {

    private final List<Method> beforeTestMethods;
    private final List<Method> afterTestMethods;
    private final List<Method> testMethods;

    Methods(List<Method> methodList) {
        this.beforeTestMethods = methodList.stream()
                .filter(m-> m.isAnnotationPresent(Before.class)).collect(Collectors.toList());
        this.afterTestMethods = methodList.stream()
                .filter(m-> m.isAnnotationPresent(After.class)).collect(Collectors.toList());
        this.testMethods = methodList.stream()
                .filter(m-> m.isAnnotationPresent(Test.class)).collect(Collectors.toList());;
    }

    public List<Method> getBeforeTestMethods() {
        return beforeTestMethods;
    }

    public List<Method> getAfterTestMethods() {
        return afterTestMethods;
    }

    public List<Method> getTestMethods() {
        return testMethods;
    }
}
